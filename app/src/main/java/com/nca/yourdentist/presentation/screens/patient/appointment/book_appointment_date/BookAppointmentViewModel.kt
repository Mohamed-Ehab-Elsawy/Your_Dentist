package com.nca.yourdentist.presentation.screens.patient.appointment.book_appointment_date

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

class BookAppointmentViewModel(
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Int>>(UiState.Idle)
    val uiState: StateFlow<UiState<Int>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()
}