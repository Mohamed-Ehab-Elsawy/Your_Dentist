package com.nca.yourdentist.presentation.screens.common.auth.complete_profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.nca.yourdentist.domain.remote.usecase.auth.UpdatePatientDataUseCase
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CompleteProfileViewModel(
    private val updatePatientDataUseCase: UpdatePatientDataUseCase,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
    val uiState: StateFlow<UiState<FirebaseUser>> = _uiState

}