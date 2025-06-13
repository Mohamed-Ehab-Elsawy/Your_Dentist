package com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.appointment_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.remote.usecase.booking.UpdateAppointmentReportUseCase
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentDetailsViewModel(
    private val updateAppointmentReport: UpdateAppointmentReportUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    fun submitNotes(appointment: Appointment, notes: String) {
        try {
            viewModelScope.launch {
                updateAppointmentReport(appointment, notes)
                _uiState.value = UiState.Success(Unit)
            }
        } catch (t: Throwable) {
            _uiState.value = UiState.Error(t)
        }
    }

}