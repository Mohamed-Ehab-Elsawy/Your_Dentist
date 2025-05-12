package com.nca.yourdentist.presentation.screens.common.auth.patient_signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.requests.AuthRequest
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.domain.remote.usecase.auth.SignupUseCase
import com.nca.yourdentist.presentation.utils.AppProviders
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PatientSignupViewModel(
    private val useCase: SignupUseCase,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
    val uiState: StateFlow<UiState<FirebaseUser>> = _uiState

    var name = mutableStateOf("")
        private set
    var email = mutableStateOf("")
        private set
    var gender = mutableStateOf("")
        private set
    var birthdate = mutableStateOf("")
        private set
    var phoneNumber = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set
    var confirmPassword = mutableStateOf("")
        private set

    var nameError = mutableStateOf<String?>(null)
        private set
    var emailError = mutableStateOf<String?>(null)
        private set
    var genderError = mutableStateOf<String?>(null)
        private set
    var birthdateError = mutableStateOf<String?>(null)
        private set
    var phoneNumberError = mutableStateOf<String?>(null)
        private set
    var passwordError = mutableStateOf<String?>(null)
        private set
    var confirmPasswordError = mutableStateOf<String?>(null)
        private set


    fun signup() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val patient = Patient(
                    name = name.value,
                    email = email.value,
                    gender = gender.value,
                    birthDate = birthdate.value,
                    phoneNumber = phoneNumber.value,
                    type = Constant.PATIENT
                )
                val request = AuthRequest(email.value, password.value, patient)
                val result = useCase.invoke(request)
                result.onSuccess { user ->
                    if (user != null && AppProviders.patient?.type == Constant.PATIENT) {
                        preferencesHelper.putPatient(AppProviders.patient!!)
                        _uiState.value = UiState.Success(user)
                    }
                }
                result.onFailure { t ->
                    _uiState.value = UiState.Error(t)
                }
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (name.value.isEmpty()) {
            nameError.value = "Enter your name"
            isValid = false
        } else nameError.value = null

        if (email.value.isEmpty()) {
            emailError.value = "Enter your email address"
            isValid = false
        } else emailError.value = null

        if (gender.value.isEmpty()) {
            genderError.value = "Select your gender"
            isValid = false
        } else genderError.value = null

        if (birthdate.value.isEmpty()) {
            birthdateError.value = "Select your birthdate"
            isValid = false
        } else birthdateError.value = null

        if (phoneNumber.value.isEmpty()) {
            phoneNumberError.value = "Enter your phone number"
            isValid = false
        } else phoneNumberError.value = null

        if (password.value.isEmpty()) {
            passwordError.value = "Enter your password"
            isValid = false
        } else if (password.value.length < 8) {
            passwordError.value = "Password must be 8 or more characters"
            isValid = false
        } else passwordError.value = null

        if (confirmPassword.value.isEmpty()) {
            confirmPasswordError.value = "Confirm your password"
            isValid = false
        } else if (confirmPassword.value != password.value) {
            confirmPasswordError.value = "Passwords do not match"
            isValid = false
        } else confirmPasswordError.value = null

        Log.e("PatientSignupViewModel", "data validation: $isValid")
        return isValid
    }

    fun appLanguage(): String =
        preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)

    fun onNameChange(newName: String) {
        name.value = newName
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onGenderChange(newGender: String) {
        gender.value = newGender
    }

    fun onBirthdateChange(newBirthdate: String) {
        birthdate.value = newBirthdate
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        phoneNumber.value = newPhoneNumber
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

}