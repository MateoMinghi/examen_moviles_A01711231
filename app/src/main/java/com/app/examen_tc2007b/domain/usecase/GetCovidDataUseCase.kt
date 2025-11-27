package com.app.examen_tc2007b.domain.usecase

import com.app.examen_tc2007b.domain.common.Result
import com.app.examen_tc2007b.domain.model.CovidStats
import com.app.examen_tc2007b.domain.repository.CovidRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCovidDataUseCase @Inject constructor(
    private val repository: CovidRepository
) {
    suspend operator fun invoke(country: String): Flow<Result<List<CovidStats>>> {
        return repository.getCovidStats(country)
    }

    suspend fun getLastCountry(): String? {
        return repository.getLastCountry()
    }
}
