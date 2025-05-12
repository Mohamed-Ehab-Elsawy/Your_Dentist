package com.nca.yourdentist.presentation.screens.common.auth.patient_login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseUser
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.requests.AuthRequest
import com.nca.yourdentist.domain.remote.usecase.auth.SignInWithEmailUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.SignInWithGoogleUseCase
import com.nca.yourdentist.presentation.utils.AppProviders
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.LanguageConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientLoginViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val googleSignInUseCase: SignInWithGoogleUseCase,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
    val uiState: StateFlow<UiState<FirebaseUser>> = _uiState

    var email = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set
    var emailError = mutableStateOf<String?>(null)
        private set
    var passwordError = mutableStateOf<String?>(null)
        private set

    fun login() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val request = AuthRequest(email.value, password.value)
                val result = signInWithEmailUseCase.invoke(request, isDentist = false)
                result.onSuccess { user ->
                    if (user != null && AppProviders.patient?.type == Constant.PATIENT) {
                        preferencesHelper.putPatient(AppProviders.patient!!)
                        _uiState.value = UiState.Success(user)
                    } else {
                        _uiState.value =
                            UiState.Error(t = Throwable(message = Constant.USER_TYPE_ERROR))
                    }
                }
                result.onFailure { _uiState.value = UiState.Error(it) }
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    fun signInWithGoogle(context: Context, serverClientId: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val credentialManager = CredentialManager.create(context)
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(serverClientId)
                    .build()
                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val credentialResponse = credentialManager.getCredential(context, request)
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credentialResponse.credential.data)

                googleSignInUseCase.invoke(googleIdTokenCredential.idToken)
                    .onSuccess { user ->
                        preferencesHelper.putPatient(AppProviders.patient!!)
                        _uiState.value = UiState.Success(user!!)
                    }
                    .onFailure { t ->
                        _uiState.value = UiState.Error(t)
                    }

            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    fun changeLanguage() {
        val currentLanguage = preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)
        val newLanguage =
            if (currentLanguage == LanguageConstants.ENGLISH) LanguageConstants.ARABIC
            else LanguageConstants.ENGLISH

        preferencesHelper.putString(PreferencesHelper.CURRENT_LANGUAGE, newLanguage)
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (email.value.isEmpty()) {
            emailError.value = "Enter your email address"
            isValid = false
        } else {
            emailError.value = null
        }

        if (password.value.isEmpty()) {
            passwordError.value = "Enter your password"
            isValid = false
        } else if (password.value.length < 8) {
            passwordError.value = "Password must be 8 or more characters"
            isValid = false
        } else {
            passwordError.value = null
        }

        return isValid
    }
}