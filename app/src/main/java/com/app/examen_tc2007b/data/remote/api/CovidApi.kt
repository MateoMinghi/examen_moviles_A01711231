package com.app.examen_tc2007b.data.remote.api

import com.app.examen_tc2007b.data.remote.dto.CovidCountryDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CovidApi {
    @GET("covid19")
    suspend fun getCovidStats(
        @Query("country") country: String,
        @Header("X-Api-Key") apiKey: String = "wLVPN1zV08lJYF7uXqgyPw==zVwp6TlVcAO1NLUf"
    ): List<CovidCountryDto>
}
