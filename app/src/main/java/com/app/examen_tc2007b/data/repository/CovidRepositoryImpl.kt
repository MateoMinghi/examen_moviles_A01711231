package com.app.examen_tc2007b.data.repository

import com.app.examen_tc2007b.data.local.CovidPreferences
import com.app.examen_tc2007b.data.mapper.toDomain
import com.app.examen_tc2007b.data.remote.api.CovidApi
import com.app.examen_tc2007b.data.remote.dto.CovidCountryDto
import com.app.examen_tc2007b.domain.common.Result
import com.app.examen_tc2007b.domain.model.CovidStats
import com.app.examen_tc2007b.domain.repository.CovidRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CovidRepositoryImpl @Inject constructor(
    private val api: CovidApi,
    private val preferences: CovidPreferences,
    private val gson: Gson
) : CovidRepository {

    override suspend fun getCovidStats(country: String): Flow<Result<List<CovidStats>>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getCovidStats(country)
            
            // Save successful response to cache
            val json = gson.toJson(response)
            preferences.saveLastJson(json)
            preferences.saveLastCountry(country)

            // Map to domain
            val stats = response.flatMap { it.toDomain() }
            emit(Result.Success(stats, isOffline = false))
        } catch (e: Exception) {
            // Try to load from cache if API fails
            val cachedJson = preferences.getLastJson()
            if (cachedJson != null) {
                try {
                    val type = object : TypeToken<List<CovidCountryDto>>() {}.type
                    val cachedResponse: List<CovidCountryDto> = gson.fromJson(cachedJson, type)
                    val stats = cachedResponse.flatMap { it.toDomain() }
                    emit(Result.Success(stats, isOffline = true))
                } catch (cacheError: Exception) {
                    emit(Result.Error("Error loading cache: ${cacheError.message}", cacheError))
                }
            } else {
                // Fallback to Mock Data if no cache available
                try {
                    val mockJson = preferences.getMockJson()
                    val type = object : TypeToken<List<CovidCountryDto>>() {}.type
                    val mockResponse: List<CovidCountryDto> = gson.fromJson(mockJson, type)
                    val stats = mockResponse.flatMap { it.toDomain() }
                    emit(Result.Success(stats, isOffline = true))
                } catch (mockError: Exception) {
                     emit(Result.Error("Network error and no cache/mock available: ${e.message}", e))
                }
            }
        }
    }

    override suspend fun getLastCountry(): String? {
        return preferences.getLastCountry()
    }

    override suspend fun getCovidDataByDate(date: String): Flow<Result<List<CovidStats>>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getDataByDate(date)
            val stats = response.map { it.toDomain(date) }
            emit(Result.Success(stats))
        } catch (e: Exception) {
            emit(Result.Error("Error fetching date data: ${e.message}", e))
        }
    }

    override suspend fun getGlobalSnapshot(): Flow<Result<List<CovidStats>>> = flow {
        emit(Result.Loading)
        try {
            val countries = listOf("USA", "UK", "France", "Germany", "Italy", "Spain", "China", "Japan", "Brazil", "India")
            val statsList = mutableListOf<CovidStats>()

            coroutineScope {
                val deferreds = countries.map { country ->
                    async(Dispatchers.IO) {
                        try {
                            val response = api.getCovidStats(country)
                            // Get the most recent data point for the country
                            val domainStats = response.map { it.toDomain() }.flatten()
                            domainStats.maxByOrNull { it.date }
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
                deferreds.forEach { deferred ->
                    deferred.await()?.let { statsList.add(it) }
                }
            }
            
            if (statsList.isNotEmpty()) {
                emit(Result.Success(statsList))
            } else {
                emit(Result.Error("Failed to fetch global snapshot"))
            }

        } catch (e: Exception) {
            emit(Result.Error("Error fetching global snapshot: ${e.message}", e))
        }
    }
}
