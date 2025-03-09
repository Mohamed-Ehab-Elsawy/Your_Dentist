package com.nca.yourdentist.di

import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.shared_preferences.PreferencesHelper
import org.koin.dsl.module

val appModule = module {
    single {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
