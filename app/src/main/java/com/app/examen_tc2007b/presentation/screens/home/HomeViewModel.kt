package com.app.examen_tc2007b.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen_tc2007b.domain.common.Result
import com.app.examen_tc2007b.domain.model.CovidStats
import com.app.examen_tc2007b.domain.usecase.GetCovidDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: List<CovidStats> = emptyList(),
    val error: String? = null,
    val lastCountry: String? = null,
    val isOffline: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCovidDataUseCase: GetCovidDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadLastCountry()
    }

    private fun loadLastCountry() {
        viewModelScope.launch {
            val last = getCovidDataUseCase.getLastCountry()
            if (last != null) {
                _uiState.value = _uiState.value.copy(lastCountry = last)
                searchCountry(last)
            }
        }
    }

    fun searchCountry(country: String) {
        if (country.isBlank()) return

        viewModelScope.launch {
            getCovidDataUseCase(country).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            data = result.data,
                            error = null,
                            isOffline = result.isOffline
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
