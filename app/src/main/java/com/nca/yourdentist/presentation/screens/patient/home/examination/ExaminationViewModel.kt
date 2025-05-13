package com.nca.yourdentist.presentation.screens.patient.home.examination

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class ExaminationViewModel(
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Int>>(UiState.Idle)
    val uiState: StateFlow<UiState<Int>> = _uiState

    fun uploadXray(photoFile: File) {

    }
}