package com.nca.yourdentist.presentation.screens.common.splash

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.domain.local.usecase.FetchLocalDentistDataUseCase
import com.nca.yourdentist.domain.local.usecase.FetchLocalPatientDataUseCase
import com.nca.yourdentist.presentation.utils.AppProviders.dentist
import com.nca.yourdentist.presentation.utils.AppProviders.patient

class SplashViewModel(
    private val fetchPatient: FetchLocalPatientDataUseCase,
    private val fetchDentist: FetchLocalDentistDataUseCase,
) : ViewModel() {
    fun fetchUser() {
        patient = fetchPatient.invoke()
        dentist = fetchDentist.invoke()
    }
}