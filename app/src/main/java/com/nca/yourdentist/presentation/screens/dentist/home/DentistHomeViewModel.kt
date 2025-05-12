package com.nca.yourdentist.presentation.screens.dentist.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.remote.usecase.booking.FetchDentistAppointmentsUseCase
import com.nca.yourdentist.presentation.utils.AppProviders
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DentistHomeViewModel(
    private val preferencesHelper: PreferencesHelper,
    private val fetchDentistAppointmentsUseCase: FetchDentistAppointmentsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Appointment>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Appointment>>> = _uiState

    fun fetchDentistAppointments() {
        _uiState.value = UiState.Loading
        try {
            viewModelScope.launch {
                val dentistId = AppProviders.dentist?.id!!
                val appointmentsResult = fetchDentistAppointmentsUseCase.invoke(dentistId)
                appointmentsResult.onSuccess { appointments ->
                    if (appointments.isNotEmpty())
                        _uiState.value = UiState.Success(appointments)
                }
            }
        } catch (t: Throwable) {
            _uiState.value = UiState.Error(t)
        }
    }

}