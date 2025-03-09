package com.nca.yourdentist.presentation.screens.dentist.auth.dentist_login

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.domain.usecase.auth.SignInWithEmailUseCase

class DentistLoginViewModel(private val useCase: SignInWithEmailUseCase): ViewModel() {
}