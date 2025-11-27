package com.app.examen_tc2007b.data.remote.api

import com.app.examen_tc2007b.data.remote.dto.CovidCountryDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CovidApi {
    @GET("covid19")
    suspend fun getCovidStats(
        @Query("country") country: String,
        @Header("X-Api-Key") apiKey: String = "rozPOy85/OvfIJyvhWQ7Zg==hwIiPTKtIkVXg7Gq"
    ): List<CovidCountryDto>
}
