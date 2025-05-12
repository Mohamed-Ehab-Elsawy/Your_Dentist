package com.nca.yourdentist.presentation.screens.patient.home.examination

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.File

class ExaminationViewModel(
    private val preferencesHelper: PreferencesHelper,
    // TODO: add usecase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Int>>(UiState.Idle)
    val uiState: StateFlow<UiState<Int>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()

    fun uploadXray(photoFile: File) {

    }
}