package com.app.examen_tc2007b.presentation.screens.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen_tc2007b.domain.common.Result
import com.app.examen_tc2007b.domain.model.CovidStats
import com.app.examen_tc2007b.domain.usecase.GetGlobalSnapshotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GlobalUiState(
    val isLoading: Boolean = false,
    val data: List<CovidStats> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val getGlobalSnapshotUseCase: GetGlobalSnapshotUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GlobalUiState())
    val uiState: StateFlow<GlobalUiState> = _uiState.asStateFlow()

    init {
        loadGlobalSnapshot()
    }

    fun loadGlobalSnapshot() {
        viewModelScope.launch {
            getGlobalSnapshotUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            data = result.data,
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
