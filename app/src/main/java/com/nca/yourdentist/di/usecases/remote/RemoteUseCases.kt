package com.nca.yourdentist.di.usecases.remote

import com.nca.yourdentist.domain.remote.repository.AuthRepository
import com.nca.yourdentist.domain.remote.repository.BookingRepository
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
import com.nca.yourdentist.domain.remote.usecase.notification.AddNotificationUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.DeleteNotificationUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.FetchNotificationsUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.UpdateNotificationUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.AddReportUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.DeleteReportUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.FetchReportsUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.UpdateReportUseCase
import org.koin.dsl.module

val authUseCaseModule = module {
    //Login
    factory { SignInWithEmailUseCase(get<AuthRepository>()) }
    factory { SignInWithGoogleUseCase(get<AuthRepository>()) }
    factory { SignupUseCase(get<AuthRepository>()) }
    //Update
    factory { UpdatePatientDataUseCase(get<AuthRepository>()) }
    //Forget Password
    factory { ForgetPasswordUseCase(get<AuthRepository>()) }
    factory { UploadQRCodeUseCase(get<AuthRepository>()) }
    //Logout
    factory { RemoteLogoutUseCase(get<AuthRepository>()) }
}

val bookingUseCaseModule = module {
    factory { FetchRemoteDentistsUseCase(get<BookingRepository>()) }
    factory { FetchAvailableAppointmentsUseCase(get<BookingRepository>()) }
    factory { BookAppointmentUseCase(get<BookingRepository>()) }
    factory { FetchDentistAppointmentsUseCase(get<BookingRepository>()) }
}

val notificationUseCaseModule = module {
    factory { AddNotificationUseCase(get()) }
    factory { UpdateNotificationUseCase(get()) }
    factory { DeleteNotificationUseCase(get()) }
    factory { FetchNotificationsUseCase(get()) }
}

val recordUseCaseModule = module {
    factory { AddReportUseCase(get()) }
    factory { UpdateReportUseCase(get()) }
    factory { DeleteReportUseCase(get()) }
    factory { FetchReportsUseCase(get()) }
}