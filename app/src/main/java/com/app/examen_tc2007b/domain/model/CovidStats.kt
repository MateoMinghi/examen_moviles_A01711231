package com.app.examen_tc2007b.domain.model

data class CovidStats(
    val country: String,
    val region: String,
    val date: String,
    val totalCases: Int,
    val newCases: Int
)
