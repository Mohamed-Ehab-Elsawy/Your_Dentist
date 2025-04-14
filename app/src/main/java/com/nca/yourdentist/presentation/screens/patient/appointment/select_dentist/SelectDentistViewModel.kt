package com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.model.Dentist
import com.nca.yourdentist.domain.usecase.booking.FetchDentistUseCase
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SelectDentistViewModel(
    private val preferencesHelper: PreferencesHelper,
    private val useCase: FetchDentistUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Dentist>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Dentist>>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()

    var selectedCity = mutableIntStateOf(0)
        private set
    var selectedArea = mutableIntStateOf(0)
        private set

    private fun fetchDentist() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val dentists = useCase.invoke(selectedCity.intValue, selectedArea.intValue)
                dentists.onSuccess {
                    if (it.isNotEmpty())
                        _uiState.value = UiState.Success(it)
                    else {
                        _snackBarMessage.emit("No dentists found at this Area")
                        _uiState.value = UiState.Idle
                    }
                }
                dentists.onFailure { t ->
                    _snackBarMessage.emit(t.localizedMessage ?: "Failed to fetch dentists")
                }

            } catch (t: Throwable) {
                _snackBarMessage.emit(t.localizedMessage ?: "Failed to fetch dentists")
            }
        }
    }

    fun onCityChange(newCity: Int) {
        selectedCity.intValue = newCity
    }

    fun onAreaChange(newArea: Int) {
        selectedArea.intValue = newArea
        fetchDentist()
    }
}