package com.nca.yourdentist.di.usecases.local

import com.nca.yourdentist.domain.local.usecase.FetchCurrentLanguageUseCase
import com.nca.yourdentist.domain.local.usecase.FetchLocalDentistDataUseCase
import com.nca.yourdentist.domain.local.usecase.FetchLocalPatientDataUseCase
import com.nca.yourdentist.domain.local.usecase.FetchNotificationsEnabledUseCase
import com.nca.yourdentist.domain.local.usecase.FetchQRCodeBitmapUseCase
import com.nca.yourdentist.domain.local.usecase.FetchReminderStateUseCase
import com.nca.yourdentist.domain.local.usecase.LocalLogoutUseCase
import com.nca.yourdentist.domain.local.usecase.PutCurrentLanguageUseCase
import com.nca.yourdentist.domain.local.usecase.PutNotificationsEnabledUseCase
import com.nca.yourdentist.domain.local.usecase.PutQRCodeBitmapUseCase
import com.nca.yourdentist.domain.local.usecase.PutReminderStateUseCase
import com.nca.yourdentist.domain.local.usecase.ScheduleToothbrushReminderUseCase
import org.koin.dsl.module

val settingsModule = module {
    //Notification State
    factory { PutNotificationsEnabledUseCase(get()) }
    factory { FetchNotificationsEnabledUseCase(get()) }
    //CurrentLanguage
    factory { PutCurrentLanguageUseCase(get()) }
    factory { FetchCurrentLanguageUseCase(get()) }
    // Toothbrush Reminder
    factory { ScheduleToothbrushReminderUseCase(get()) }
    factory { PutReminderStateUseCase(get()) }
    factory { FetchReminderStateUseCase(get()) }
    // Patient
    factory { FetchLocalPatientDataUseCase(get()) }
    factory { FetchQRCodeBitmapUseCase(get()) }
    factory { PutQRCodeBitmapUseCase(get()) }
    factory { LocalLogoutUseCase(get()) }
    // Dentist
    factory { FetchLocalDentistDataUseCase(get()) }

}