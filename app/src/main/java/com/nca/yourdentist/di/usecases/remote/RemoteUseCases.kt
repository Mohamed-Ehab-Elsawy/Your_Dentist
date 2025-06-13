package com.nca.yourdentist.di.usecases.remote

import com.nca.yourdentist.domain.remote.repository.AppointmentsRepository
import com.nca.yourdentist.domain.remote.repository.AuthRepository
import com.nca.yourdentist.domain.remote.repository.DetectCariesRepository
import com.nca.yourdentist.domain.remote.usecase.auth.ForgetPasswordUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.RemoteLogoutUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.SignInWithEmailUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.SignInWithGoogleUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.SignupUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.UpdatePatientDataUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.UploadQRCodeUseCase
import com.nca.yourdentist.domain.remote.usecase.booking.BookAppointmentUseCase
import com.nca.yourdentist.domain.remote.usecase.booking.FetchAvailableAppointmentsUseCase
import com.nca.yourdentist.domain.remote.usecase.booking.FetchDentistAppointmentsUseCase
import com.nca.yourdentist.domain.remote.usecase.booking.FetchRemoteDentistsUseCase
import com.nca.yourdentist.domain.remote.usecase.booking.UpdateAppointmentReportUseCase
import com.nca.yourdentist.domain.remote.usecase.detect_caries.DetectCariesUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.DeleteNotificationUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.ObserveNotificationsUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.UpdateNotificationUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.DeleteReportUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.ObserveReportsUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.RateDentistUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.UpdateReportUseCase
import org.koin.dsl.module

val remoteUseCasesModule = module {
    // Auth
    factory { SignInWithEmailUseCase(get<AuthRepository>()) }
    factory { SignInWithGoogleUseCase(get<AuthRepository>()) }
    factory { SignupUseCase(get<AuthRepository>()) }
    factory { UpdatePatientDataUseCase(get<AuthRepository>()) }
    factory { ForgetPasswordUseCase(get<AuthRepository>()) }
    factory { UploadQRCodeUseCase(get<AuthRepository>()) }
    factory { RemoteLogoutUseCase(get<AuthRepository>()) }
    // Detect Caries
    factory { DetectCariesUseCase(get<DetectCariesRepository>()) }
    // Booking
    factory { FetchRemoteDentistsUseCase(get<AppointmentsRepository>()) }
    factory { FetchAvailableAppointmentsUseCase(get<AppointmentsRepository>()) }
    factory { BookAppointmentUseCase(get<AppointmentsRepository>()) }
    factory { FetchDentistAppointmentsUseCase(get<AppointmentsRepository>()) }
    factory { UpdateAppointmentReportUseCase(get<AppointmentsRepository>()) }
    // Notification
    factory { UpdateNotificationUseCase(get()) }
    factory { DeleteNotificationUseCase(get()) }
    factory { ObserveNotificationsUseCase(get()) }
    // Reports
    factory { UpdateReportUseCase(get()) }
    factory { DeleteReportUseCase(get()) }
    factory { ObserveReportsUseCase(get()) }
    factory { RateDentistUseCase(get()) }
}