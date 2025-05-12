package com.nca.yourdentist.data.local.repository

import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.domain.local.repository.SettingsRepository

class SettingsRepositoryImpl(private val preferencesHelper: PreferencesHelper) :
    SettingsRepository {

    override fun isNotificationsEnabled(): Boolean =
        preferencesHelper.fetchBoolean(PreferencesHelper.NOTIFICATION_ENABLED)


    override fun setNotificationsEnabled(value: Boolean) =
        preferencesHelper.putBoolean(PreferencesHelper.NOTIFICATION_ENABLED, value)

    override fun fetchLanguage(): String =
        preferencesHelper.fetchString(PreferencesHelper.CURRENT_LANGUAGE)

    override fun putLanguage(value: String) {
        preferencesHelper.putString(PreferencesHelper.CURRENT_LANGUAGE, value)
    }

    override fun logout() {
        preferencesHelper.clearData()
    }
}