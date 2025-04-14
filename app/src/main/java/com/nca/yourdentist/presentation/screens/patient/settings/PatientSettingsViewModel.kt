package com.nca.yourdentist.presentation.screens.patient.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.domain.usecase.auth.LogoutUseCase
import com.nca.yourdentist.utils.AppProviders
import com.nca.yourdentist.utils.Constant
import kotlinx.coroutines.launch

class PatientSettingsViewModel(
    private val preferencesHelper: PreferencesHelper,
    private val usecase: LogoutUseCase
) : ViewModel() {

    fun changeLanguage() {
        val currentLanguage = preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)
        val newLanguage =
            if (currentLanguage == Constant.ENGLISH_LANGUAGE_KEY) Constant.ARABIC_LANGUAGE_KEY
            else Constant.ENGLISH_LANGUAGE_KEY

        preferencesHelper.putString(PreferencesHelper.CURRENT_LANGUAGE, newLanguage)
    }

    fun logout() {
        viewModelScope.launch {
            preferencesHelper.clearData()
            resetProviders()
            usecase.invoke()
        }
    }

    private fun resetProviders() {
        AppProviders.patient = null
        AppProviders.patientQRCodeBitmap = null
        AppProviders.dentist = null
        AppProviders.finalResult = null
        Log.e("PatientSettingsViewModel", "Reset providers")
    }
}