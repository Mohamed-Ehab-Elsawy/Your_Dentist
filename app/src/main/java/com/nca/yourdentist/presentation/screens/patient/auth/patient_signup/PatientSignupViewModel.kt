package com.nca.yourdentist.presentation.screens.patient.auth.patient_signup

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.domain.usecase.auth.SignupUseCase


class PatientSignupViewModel(
    private val useCase: SignupUseCase
): ViewModel() {
}