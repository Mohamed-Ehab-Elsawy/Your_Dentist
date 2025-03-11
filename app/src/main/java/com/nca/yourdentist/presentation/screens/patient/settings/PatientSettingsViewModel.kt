package com.nca.yourdentist.presentation.screens.patient.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.model.Patient
import com.nca.yourdentist.data.shared_preferences.PreferencesHelper
import com.nca.yourdentist.domain.usecase.auth.LogoutUseCase
import kotlinx.coroutines.launch

class PatientSettingsViewModel(
    private val preferencesHelper: PreferencesHelper,
    private val usecase: LogoutUseCase
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            preferencesHelper.savePatient(Patient())
            usecase.invoke()
        }
    }

    fun changeLanguage() {
        val currentLanguage = preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)
        val newLanguage = if (currentLanguage == "en") "ar" else "en"

        preferencesHelper.putString(PreferencesHelper.CURRENT_LANGUAGE, newLanguage)
    }
}