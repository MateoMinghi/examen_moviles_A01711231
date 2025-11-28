package com.app.examen_tc2007b.domain.repository

import com.app.examen_tc2007b.domain.common.Result
import com.app.examen_tc2007b.domain.model.CovidStats
import kotlinx.coroutines.flow.Flow

interface CovidRepository {
    suspend fun getCovidStats(country: String): Flow<Result<List<CovidStats>>>
    suspend fun getLastCountry(): String?
    suspend fun getCovidDataByDate(date: String): Flow<Result<List<CovidStats>>>
    suspend fun getGlobalSnapshot(): Flow<Result<List<CovidStats>>>
}
