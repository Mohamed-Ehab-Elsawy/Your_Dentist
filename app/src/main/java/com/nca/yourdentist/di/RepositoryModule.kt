package com.nca.yourdentist.di

import com.nca.yourdentist.data.local.repository.HomeRepositoryImpl
import com.nca.yourdentist.data.local.repository.SettingsRepositoryImpl
import com.nca.yourdentist.data.remote.repository.AuthRepositoryImpl
import com.nca.yourdentist.data.remote.repository.BookingRepositoryImpl
import com.nca.yourdentist.data.remote.repository.NotificationsRepositoryImpl
import com.nca.yourdentist.data.remote.repository.ReportsRepositoryImpl
import com.nca.yourdentist.domain.local.repository.HomeRepository
import com.nca.yourdentist.domain.local.repository.SettingsRepository
import com.nca.yourdentist.domain.remote.repository.AuthRepository
import com.nca.yourdentist.domain.remote.repository.BookingRepository
import com.nca.yourdentist.domain.remote.repository.NotificationsRepository
import com.nca.yourdentist.domain.remote.repository.ReportsRepository
import org.koin.dsl.module

val repositoryModule = module {
    //Remote
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get()) }
    single<BookingRepository> { BookingRepositoryImpl(get()) }
    single<NotificationsRepository> { NotificationsRepositoryImpl(get()) }
    single<ReportsRepository> { ReportsRepositoryImpl(get()) }
    //Local
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}