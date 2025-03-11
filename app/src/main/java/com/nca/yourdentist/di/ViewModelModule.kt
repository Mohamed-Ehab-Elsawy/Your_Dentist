package com.nca.yourdentist.di

import com.nca.yourdentist.presentation.screens.common.auth.forget_password.ForgetPasswordViewModel
import com.nca.yourdentist.presentation.screens.dentist.auth.dentist_login.DentistLoginViewModel
import com.nca.yourdentist.presentation.screens.dentist.home.DentistHomeViewModel
import com.nca.yourdentist.presentation.screens.patient.auth.complete_profile.CompleteProfileViewModel
import com.nca.yourdentist.presentation.screens.patient.auth.patient_login.PatientLoginViewModel
import com.nca.yourdentist.presentation.screens.patient.auth.patient_signup.PatientSignupViewModel
import com.nca.yourdentist.presentation.screens.patient.home.PatientHomeViewModel
import com.nca.yourdentist.presentation.screens.patient.settings.PatientSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    //Patient
    viewModel { PatientLoginViewModel(get(), get(), get()) }
    viewModel { CompleteProfileViewModel(get()) }
    viewModel { ForgetPasswordViewModel(get()) }
    viewModel { PatientSignupViewModel(get(), get()) }
    viewModel { PatientHomeViewModel(get()) }
    viewModel { PatientSettingsViewModel(get(), get()) }

    //Dentist
    viewModel { DentistLoginViewModel(get(), get()) }
    viewModel { DentistHomeViewModel(get()) }
}