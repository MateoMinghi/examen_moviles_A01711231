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
            emit(Result.Success(stats))
        } catch (e: Exception) {
            // Try to load from cache if API fails
            val cachedJson = preferences.getLastJson()
            if (cachedJson != null) {
                try {
                    val type = object : TypeToken<List<CovidCountryDto>>() {}.type
                    val cachedResponse: List<CovidCountryDto> = gson.fromJson(cachedJson, type)
                    val stats = cachedResponse.flatMap { it.toDomain() }
                    emit(Result.Success(stats)) // Or maybe emit a specific "CachedSuccess" if needed, but Success is fine for now
                } catch (cacheError: Exception) {
                    emit(Result.Error("Error loading cache: ${cacheError.message}", cacheError))
                }
            } else {
                emit(Result.Error("Network error and no cache available: ${e.message}", e))
            }
        }
    }

    override suspend fun getLastCountry(): String? {
        return preferences.getLastCountry()
    }
}
