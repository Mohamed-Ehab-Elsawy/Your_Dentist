package com.nca.yourdentist.domain.local.usecase

import com.nca.yourdentist.domain.local.repository.SettingsRepository

class PutNotificationsEnabledUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(value: Boolean) = settingsRepository.setNotificationsEnabled(value)
}

class FetchNotificationsEnabledUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.isNotificationsEnabled()
}

class PutCurrentLanguageUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(value: String) = settingsRepository.putLanguage(value)
}

class FetchCurrentLanguageUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.fetchLanguage()
}

class LocalLogoutUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.logout()
}