package com.nca.yourdentist.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.nca.yourdentist.di.appModule
import com.nca.yourdentist.di.authUseCaseModule
import com.nca.yourdentist.di.firebaseModule
import com.nca.yourdentist.di.repositoryModule
import com.nca.yourdentist.di.sharedPreferencesModule
import com.nca.yourdentist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YourDentist : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Enforce Light Mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            androidLogger()
            androidContext(this@YourDentist)
            modules(
                listOf(
                    appModule,
                    firebaseModule,
                    repositoryModule,
                    sharedPreferencesModule,
                    authUseCaseModule,
                    viewModelModule
                )
            )
        }
    }
}