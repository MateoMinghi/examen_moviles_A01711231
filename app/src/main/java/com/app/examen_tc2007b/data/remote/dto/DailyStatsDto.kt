package com.app.examen_tc2007b.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DailyStatsDto(
    @SerializedName("total") val total: Int,
    @SerializedName("new") val newCases: Int
)
