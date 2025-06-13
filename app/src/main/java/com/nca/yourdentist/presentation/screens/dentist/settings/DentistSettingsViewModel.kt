package com.nca.yourdentist.presentation.screens.dentist.settings

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.domain.local.usecase.FetchCurrentLanguageUseCase
import com.nca.yourdentist.domain.local.usecase.FetchNotificationsEnabledUseCase
import com.nca.yourdentist.domain.local.usecase.LocalLogoutUseCase
import com.nca.yourdentist.domain.local.usecase.PutCurrentLanguageUseCase
import com.nca.yourdentist.domain.local.usecase.PutNotificationsEnabledUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.RemoteLogoutUseCase
import com.nca.yourdentist.presentation.utils.Provider
import com.nca.yourdentist.utils.LanguageConstants
import kotlinx.coroutines.launch

class DentistSettingsViewModel(
    fetchNotificationsEnabled: FetchNotificationsEnabledUseCase,
    fetchCurrentLanguage: FetchCurrentLanguageUseCase,
    private val remoteLogout: RemoteLogoutUseCase,
    private val setNotificationsEnabled: PutNotificationsEnabledUseCase,
    private val putCurrentLanguage: PutCurrentLanguageUseCase,
    private val localLogout: LocalLogoutUseCase,
) : ViewModel() {

    private val currentLanguage = mutableStateOf(
        fetchCurrentLanguage.invoke()
    )
    val language: State<String> = currentLanguage

    private val _notificationsEnabled = mutableStateOf(
        fetchNotificationsEnabled.invoke()
    )
    val notificationsEnabled: State<Boolean> = _notificationsEnabled


    fun toggleNotifications() {
        _notificationsEnabled.value = !_notificationsEnabled.value
        setNotificationsEnabled.invoke(_notificationsEnabled.value)
    }


    fun changeLanguage() {
        val selectedLanguage =
            if (currentLanguage.value == LanguageConstants.ENGLISH) LanguageConstants.ARABIC
            else LanguageConstants.ENGLISH

        currentLanguage.value = selectedLanguage
        putCurrentLanguage.invoke(selectedLanguage)
        Log.d("SettingsViewModel", "Language changed to: $selectedLanguage")
    }

    fun logout() {
        resetProviders()
        localLogout.invoke()
        viewModelScope.launch { remoteLogout.invoke() }
    }

    private fun resetProviders() {
        Provider.dentist = null
        Provider.patient = null
        Provider.patientQRCodeBitmap = null
        Log.e("DentistSettingsViewModel", "Reset providers")
    }
}