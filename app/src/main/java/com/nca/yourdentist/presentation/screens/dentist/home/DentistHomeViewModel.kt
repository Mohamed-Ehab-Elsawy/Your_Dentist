package com.nca.yourdentist.presentation.screens.dentist.home

import androidx.lifecycle.ViewModel
import com.nca.yourdentist.domain.usecase.auth.LogoutUseCase

class DentistHomeViewModel(
    private val useCase: LogoutUseCase
) : ViewModel() {

}