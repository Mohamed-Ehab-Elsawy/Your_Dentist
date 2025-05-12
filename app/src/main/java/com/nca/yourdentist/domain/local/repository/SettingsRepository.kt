package com.nca.yourdentist.domain.local.repository

interface SettingsRepository {
    fun isNotificationsEnabled(): Boolean
    fun setNotificationsEnabled(value: Boolean)
    fun fetchLanguage(): String
    fun putLanguage(value: String)
    fun logout()
}