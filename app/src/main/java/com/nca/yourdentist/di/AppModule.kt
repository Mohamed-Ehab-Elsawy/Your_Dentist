package com.nca.yourdentist.di

import androidx.appcompat.app.AppCompatDelegate
import com.nca.yourdentist.data.network.NetworkMonitor
import com.nca.yourdentist.data.notification.NotificationHelper
import com.nca.yourdentist.data.scheduler.ReminderSchedulerImpl
import com.nca.yourdentist.domain.local.repository.ReminderScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
    single { NetworkMonitor(androidContext()) }
    single { NotificationHelper(get()) }

    single<ReminderScheduler> { ReminderSchedulerImpl(get()) }
}