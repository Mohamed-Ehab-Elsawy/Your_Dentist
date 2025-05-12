package com.nca.yourdentist.presentation.screens.patient.home.appointment.book_appointment_date

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.models.AppointmentStatus
import com.nca.yourdentist.domain.remote.usecase.booking.BookAppointmentUseCase
import com.nca.yourdentist.domain.remote.usecase.booking.FetchAvailableAppointmentsUseCase
import com.nca.yourdentist.presentation.utils.AppProviders
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookAppointmentViewModel(
    private val preferencesHelper: PreferencesHelper,
    private val fetchAppointments: FetchAvailableAppointmentsUseCase,
    private val bookAppointment: BookAppointmentUseCase
) : ViewModel() {

    private val _appointmentsUiState = MutableStateFlow<UiState<List<Appointment>>>(UiState.Idle)
    val appointmentsUiState: StateFlow<UiState<List<Appointment>>> = _appointmentsUiState

    private val _bookingUiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val bookingUiState: StateFlow<UiState<String>> = _bookingUiState


    fun fetchDentistAppointments(dentistId: String) {
        _appointmentsUiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = fetchAppointments.invoke(dentistId)
                result.onSuccess { appointments ->
                    _appointmentsUiState.value = UiState.Success(appointments)
                }
                result.onFailure {
                    _appointmentsUiState.value = UiState.Error(Throwable(it.localizedMessage))
                }
            } catch (t: Throwable) {
                _appointmentsUiState.value = UiState.Error(t)
            }
        }
    }

    fun bookAppointment(
        appointment: Appointment
    ) {
        _bookingUiState.value = UiState.Loading
        try {
            appointment.patientName = AppProviders.patient?.name
            appointment.patientId = AppProviders.patient?.id
            appointment.status = AppointmentStatus.BOOKED.name.lowercase()
            Log.e("BookAppointmentViewModel", "Appointment: ${appointment.copy()}")
            viewModelScope.launch {
                try {
                    val result = bookAppointment.invoke(appointment)
                    result.onSuccess {
                        _bookingUiState.value = UiState.Success(it)
                    }
                    result.onFailure { it ->
                        _bookingUiState.value = UiState.Error(Throwable(it.message))
                    }
                } catch (t: Throwable) {
                    _bookingUiState.value = UiState.Error(t)
                }
            }
        } catch (t: Throwable) {
            _bookingUiState.value = UiState.Error(t)
        }
    }
}