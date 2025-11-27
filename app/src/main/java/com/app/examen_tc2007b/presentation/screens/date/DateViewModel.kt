package com.app.examen_tc2007b.presentation.screens.date

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen_tc2007b.domain.common.Result
import com.app.examen_tc2007b.domain.model.CovidStats
import com.app.examen_tc2007b.domain.usecase.GetCovidDataByDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DateUiState(
    val isLoading: Boolean = false,
    val data: List<CovidStats> = emptyList(),
    val error: String? = null,
    val selectedDate: String? = null
)

@HiltViewModel
class DateViewModel @Inject constructor(
    private val getCovidDataByDateUseCase: GetCovidDataByDateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DateUiState())
    val uiState: StateFlow<DateUiState> = _uiState.asStateFlow()

    fun searchByDate(date: String) {
        if (date.isBlank()) return
        
        _uiState.value = _uiState.value.copy(selectedDate = date)

        viewModelScope.launch {
            getCovidDataByDateUseCase(date).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                    }
                    is Result.Success -> {
                        // Sort by new cases descending
                        val sortedData = result.data.sortedByDescending { it.newCases }
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            data = sortedData,
                            error = null
                        )
                    }
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
}
