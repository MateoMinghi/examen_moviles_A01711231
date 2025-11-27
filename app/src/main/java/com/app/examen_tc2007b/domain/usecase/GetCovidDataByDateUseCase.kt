package com.app.examen_tc2007b.domain.usecase

import com.app.examen_tc2007b.domain.common.Result
import com.app.examen_tc2007b.domain.model.CovidStats
import com.app.examen_tc2007b.domain.repository.CovidRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCovidDataByDateUseCase @Inject constructor(
    private val repository: CovidRepository
) {
    suspend operator fun invoke(date: String): Flow<Result<List<CovidStats>>> {
        return repository.getCovidDataByDate(date)
    }
}
