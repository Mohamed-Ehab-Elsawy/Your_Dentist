package com.nca.yourdentist.di.usecases.local

import com.nca.yourdentist.domain.local.usecase.FetchCurrentLanguageUseCase
import com.nca.yourdentist.domain.local.usecase.FetchNotificationsEnabledUseCase
import com.nca.yourdentist.domain.local.usecase.LocalLogoutUseCase
import com.nca.yourdentist.domain.local.usecase.PutCurrentLanguageUseCase
import com.nca.yourdentist.domain.local.usecase.PutNotificationsEnabledUseCase
import org.koin.dsl.module

val settingsModule = module {
    factory { PutNotificationsEnabledUseCase(get()) }
    factory { FetchNotificationsEnabledUseCase(get()) }

    factory { PutCurrentLanguageUseCase(get()) }
    factory { FetchCurrentLanguageUseCase(get()) }

    factory { LocalLogoutUseCase(get()) }
}