package com.nca.yourdentist.presentation.screens.common.splash

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.presentation.utils.AppProviders.dentist
import com.nca.yourdentist.presentation.utils.AppProviders.patient

class SplashViewModel(
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    fun fetchUser() {
        patient = preferencesHelper.fetchPatient()
        dentist = preferencesHelper.fetchDentist()
    }

}