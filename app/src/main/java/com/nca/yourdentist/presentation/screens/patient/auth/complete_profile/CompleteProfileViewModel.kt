package com.nca.yourdentist.presentation.screens.patient.auth.complete_profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.domain.usecase.auth.UpdatePatientDataUseCase
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

class CompleteProfileViewModel(
    private val updatePatientDataUseCase: UpdatePatientDataUseCase,
    private val preferencesHelper: PreferencesHelper
) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
    val uiState: StateFlow<UiState<FirebaseUser>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()



}