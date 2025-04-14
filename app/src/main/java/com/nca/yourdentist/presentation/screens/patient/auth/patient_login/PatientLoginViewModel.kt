package com.nca.yourdentist.presentation.screens.patient.auth.patient_login

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
import com.nca.yourdentist.data.model.requests.AuthRequest
import com.nca.yourdentist.domain.usecase.auth.SignInWithEmailUseCase
import com.nca.yourdentist.domain.usecase.auth.SignInWithGoogleUseCase
import com.nca.yourdentist.utils.AppProviders
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PatientLoginViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val googleSignInUseCase: SignInWithGoogleUseCase,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
    val uiState: StateFlow<UiState<FirebaseUser>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()

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
                        _snackBarMessage.emit("User not found")
                        _uiState.value = UiState.Idle // Reset state after error
                    }
                }
                result.onFailure { t ->
                    _snackBarMessage.emit(t.localizedMessage ?: "Login failed")
                    _uiState.value = UiState.Idle // Reset state after error
                }
            } catch (t: Throwable) {
                _snackBarMessage.emit(t.localizedMessage ?: "Login failed")
                _uiState.value = UiState.Idle // Reset state after error
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
                        _snackBarMessage.emit(t.localizedMessage ?: "Google Sign-In Failed")
                        _uiState.value = UiState.Idle
                    }

            } catch (t: Throwable) {
                _snackBarMessage.emit(t.localizedMessage ?: "Google Sign-In Failed")
                _uiState.value = UiState.Idle
            }
        }
    }

    fun changeLanguage() {
        val currentLanguage = preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)
        val newLanguage =
            if (currentLanguage == Constant.ENGLISH_LANGUAGE_KEY) Constant.ARABIC_LANGUAGE_KEY
            else Constant.ENGLISH_LANGUAGE_KEY

        preferencesHelper.putString(PreferencesHelper.CURRENT_LANGUAGE, newLanguage)
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

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }
}