package com.nca.yourdentist.di

import androidx.appcompat.app.AppCompatDelegate
import com.nca.yourdentist.data.network.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
    single { NetworkMonitor(androidContext()) }


}
