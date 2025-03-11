package com.nca.yourdentist.presentation.screens.common.auth.forget_password

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.domain.usecase.auth.ForgetPasswordUseCase
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val useCase: ForgetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Int>>(UiState.Idle)
    val uiState: StateFlow<UiState<Int>> = _uiState

    var email = mutableStateOf("")
        private set
    var emailError = mutableStateOf<String?>(null)
        private set

    fun sendResetPasswordEmail() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                useCase.invoke(email.value)
                _uiState.value = UiState.Success(1)
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (email.value.isEmpty()) {
            emailError.value = "Enter your email address"
            isValid = false
        } else {
            emailError.value = null
        }

        return isValid
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

}