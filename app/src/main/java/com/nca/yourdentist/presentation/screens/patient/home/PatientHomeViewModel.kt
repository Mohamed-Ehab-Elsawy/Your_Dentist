package com.nca.yourdentist.presentation.screens.patient.home

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.domain.usecase.auth.LogoutUseCase

class PatientHomeViewModel(
    private val useCase: LogoutUseCase
) : ViewModel() {

}