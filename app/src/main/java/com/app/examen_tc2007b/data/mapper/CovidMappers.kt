package com.app.examen_tc2007b.data.mapper

import com.app.examen_tc2007b.data.remote.dto.CovidCountryDto
import com.app.examen_tc2007b.domain.model.CovidStats

fun CovidCountryDto.toDomain(): List<CovidStats> {
    return cases.map { (date, stats) ->
        CovidStats(
            country = country,
            region = region,
            date = date,
            totalCases = stats.total,
            newCases = stats.newCases
        )
    }
}
