package com.nca.yourdentist.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.local.utils.PreferenceKeys
import com.nca.yourdentist.data.network.NetworkMonitor
import com.nca.yourdentist.data.notification.NotificationHelper
import com.nca.yourdentist.data.remote.api.DetectCariesAPI
import com.nca.yourdentist.data.remote.api.DetectCariesAPIImpl
import com.nca.yourdentist.data.scheduler.ReminderSchedulerImpl
import com.nca.yourdentist.domain.local.repository.ReminderScheduler
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // Set Default Light Mode
    single { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
    // Network Monitor DI
    single { NetworkMonitor(androidContext()) }
    // Notification DI
    single { NotificationHelper(get()) }
    // Scheduler DI
    single<ReminderScheduler> { ReminderSchedulerImpl(get()) }
    // Firebase DI
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    //Shared Preferences DI
    single { PreferencesHelper(get()) }
    single<SharedPreferences> {
        val context: Context = androidContext()
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PreferenceKeys.SHARED_PREFERENCE_KEY,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    single<DetectCariesAPI> { DetectCariesAPIImpl(get()) }
}