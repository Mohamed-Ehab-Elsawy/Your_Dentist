package com.nca.yourdentist.di

import com.nca.yourdentist.presentation.screens.common.auth.complete_profile.CompleteProfileViewModel
import com.nca.yourdentist.presentation.screens.common.auth.dentist_login.DentistLoginViewModel
import com.nca.yourdentist.presentation.screens.common.auth.forget_password.ForgetPasswordViewModel
import com.nca.yourdentist.presentation.screens.common.auth.patient_login.PatientLoginViewModel
import com.nca.yourdentist.presentation.screens.common.auth.patient_signup.PatientSignupViewModel
import com.nca.yourdentist.presentation.screens.common.select_type.SelectTypeViewModel
import com.nca.yourdentist.presentation.screens.common.splash.SplashViewModel
import com.nca.yourdentist.presentation.screens.dentist.home.DentistHomeViewModel
import com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.appointment_details.AppointmentDetailsViewModel
import com.nca.yourdentist.presentation.screens.dentist.notification.DentistNotificationViewModel
import com.nca.yourdentist.presentation.screens.dentist.settings.DentistSettingsViewModel
import com.nca.yourdentist.presentation.screens.patient.appointment.book_appointment_date.BookAppointmentViewModel
import com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.SelectDentistViewModel
import com.nca.yourdentist.presentation.screens.patient.caries_detection.ExaminationViewModel
import com.nca.yourdentist.presentation.screens.patient.home.PatientHomeViewModel
import com.nca.yourdentist.presentation.screens.patient.notification.PatientNotificationViewModel
import com.nca.yourdentist.presentation.screens.patient.reports.PatientReportsViewModel
import com.nca.yourdentist.presentation.screens.patient.settings.PatientSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // Common
    viewModel { SplashViewModel(get(), get()) }
    viewModel { SelectTypeViewModel(get(), get()) }
    viewModel { PatientLoginViewModel(get(), get(), get(), get()) }
    viewModel { ForgetPasswordViewModel(get()) }
    viewModel { CompleteProfileViewModel(get()) }
    viewModel { PatientSignupViewModel(get()) }
    viewModel { DentistLoginViewModel(get(), get(), get()) }

    //Patient
    viewModel { PatientHomeViewModel(get(), get(), get(), get(), get()) }
    viewModel { ExaminationViewModel(get()) }
    viewModel { SelectDentistViewModel(get(), get()) }
    viewModel { BookAppointmentViewModel(get(), get()) }
    viewModel { PatientNotificationViewModel(get(), get(), get(), get()) }
    viewModel { PatientReportsViewModel(get(), get(), get(), get(), get()) }
    viewModel { PatientSettingsViewModel(get(), get(), get(), get(), get(), get()) }

    //Dentist
    viewModel { DentistHomeViewModel(get()) }
    viewModel { AppointmentDetailsViewModel(get()) }
    viewModel { DentistNotificationViewModel(get(), get(), get(), get()) }
    viewModel { DentistSettingsViewModel(get(), get(), get(), get(), get(), get()) }
}