package com.app.examen_tc2007b.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CovidCountryDto(
    @SerializedName("country") val country: String,
    @SerializedName("region") val region: String,
    @SerializedName("cases") val cases: Map<String, DailyStatsDto>
)
