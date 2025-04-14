package com.nca.yourdentist.presentation.screens.patient.examination.upload_image

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

class UploadRadiographViewModel(
    private val preferencesHelper: PreferencesHelper,
    // TODO: add usecase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Int>>(UiState.Idle)
    val uiState: StateFlow<UiState<Int>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()
}