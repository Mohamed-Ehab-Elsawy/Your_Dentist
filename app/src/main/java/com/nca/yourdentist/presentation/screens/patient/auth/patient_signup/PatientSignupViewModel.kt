package com.nca.yourdentist.presentation.screens.patient.auth.patient_signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nca.yourdentist.data.model.Patient
import com.nca.yourdentist.data.model.requests.AuthRequest
import com.nca.yourdentist.data.shared_preferences.PreferencesHelper
import com.nca.yourdentist.domain.usecase.auth.SignupUseCase
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.UiState
import com.nca.yourdentist.utils.providers.PatientProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class PatientSignupViewModel(
    private val useCase: SignupUseCase,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
    val uiState: StateFlow<UiState<FirebaseUser>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()

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
                    if (user != null && PatientProvider.patient?.type == Constant.PATIENT) {
                        preferencesHelper.savePatient(PatientProvider.patient!!)
                        _uiState.value = UiState.Success(user)
                    }
                }
                result.onFailure { t ->
                    _snackBarMessage.emit(t.localizedMessage ?: "Signup failed")
                    _uiState.value = UiState.Idle // Reset state after error
                }
            } catch (t: Throwable) {
                _snackBarMessage.emit(t.localizedMessage ?: "Signup failed")
                _uiState.value = UiState.Idle // Reset state after error
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