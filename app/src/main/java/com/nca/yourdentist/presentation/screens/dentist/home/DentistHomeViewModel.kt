package com.nca.yourdentist.presentation.screens.dentist.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.remote.usecase.booking.FetchDentistAppointmentsUseCase
import com.nca.yourdentist.presentation.utils.Provider
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DentistHomeViewModel(
    private val fetchDentistAppointmentsUseCase: FetchDentistAppointmentsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Appointment>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Appointment>>> = _uiState

    fun fetchDentistAppointments() {
        Log.e("DentistHomeViewModel", "fetchDentistAppointments: called")
        _uiState.value = UiState.Loading
        try {
            viewModelScope.launch {
                val dentistId = Provider.dentist?.id!!
                val appointmentsResult = fetchDentistAppointmentsUseCase.invoke(dentistId)
                appointmentsResult.onSuccess { appointments ->
                    if (appointments.isNotEmpty())
                        _uiState.value = UiState.Success(appointments)
                    else
                        _uiState.value = UiState.Error(Throwable("No appointments found"))
                }.onFailure {
                    _uiState.value = UiState.Error(it)
                }
            }
        } catch (t: Throwable) {
            _uiState.value = UiState.Error(t)
        }
    }
}