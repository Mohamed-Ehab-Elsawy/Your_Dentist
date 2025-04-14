package com.nca.yourdentist.di

import com.nca.yourdentist.presentation.screens.common.auth.forget_password.ForgetPasswordViewModel
import com.nca.yourdentist.presentation.screens.common.auth.select_type.SelectTypeViewModel
import com.nca.yourdentist.presentation.screens.dentist.auth.dentist_login.DentistLoginViewModel
import com.nca.yourdentist.presentation.screens.dentist.home.DentistHomeViewModel
import com.nca.yourdentist.presentation.screens.dentist.notification.DentistNotificationViewModel
import com.nca.yourdentist.presentation.screens.patient.appointment.book_appointment_date.BookAppointmentViewModel
import com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.SelectDentistViewModel
import com.nca.yourdentist.presentation.screens.patient.auth.complete_profile.CompleteProfileViewModel
import com.nca.yourdentist.presentation.screens.patient.auth.patient_login.PatientLoginViewModel
import com.nca.yourdentist.presentation.screens.patient.auth.patient_signup.PatientSignupViewModel
import com.nca.yourdentist.presentation.screens.patient.examination.questionnaire.QuestionnaireViewModel
import com.nca.yourdentist.presentation.screens.patient.examination.results.PatientResultsViewModel
import com.nca.yourdentist.presentation.screens.patient.examination.upload_image.UploadRadiographViewModel
import com.nca.yourdentist.presentation.screens.patient.home.PatientHomeViewModel
import com.nca.yourdentist.presentation.screens.patient.notification.PatientNotificationViewModel
import com.nca.yourdentist.presentation.screens.patient.records.PatientRecordsViewModel
import com.nca.yourdentist.presentation.screens.patient.settings.PatientSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SelectTypeViewModel(get()) }
    //Patient
    viewModel { PatientLoginViewModel(get(), get(), get()) }
    viewModel { CompleteProfileViewModel(get(), get()) }
    viewModel { ForgetPasswordViewModel(get()) }
    viewModel { PatientSignupViewModel(get(), get()) }
    viewModel { PatientHomeViewModel(get(), get()) }
    viewModel { UploadRadiographViewModel(get()) }
    viewModel { QuestionnaireViewModel(get()) }
    viewModel { SelectDentistViewModel(get(), get()) }
    viewModel { BookAppointmentViewModel(get()) }
    viewModel { PatientNotificationViewModel(get()) }
    viewModel { PatientRecordsViewModel(get()) }
    viewModel { PatientResultsViewModel(get()) }
    viewModel { PatientSettingsViewModel(get(), get()) }

    //Dentist
    viewModel { DentistLoginViewModel(get(), get()) }
    viewModel { DentistHomeViewModel(get()) }
    viewModel { DentistNotificationViewModel(get()) }
}