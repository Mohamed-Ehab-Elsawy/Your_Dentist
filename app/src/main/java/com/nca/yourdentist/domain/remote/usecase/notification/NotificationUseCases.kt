package com.nca.yourdentist.domain.remote.usecase.notification

import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.domain.remote.repository.NotificationsRepository

class AddNotificationUseCase(private val notificationRepository: NotificationsRepository) {
    suspend operator fun invoke(appNotification: AppNotification) =
        notificationRepository.addNotification(appNotification)
}

class UpdateNotificationUseCase(private val notificationRepository: NotificationsRepository) {
    suspend operator fun invoke(appNotification: AppNotification) =
        notificationRepository.updateNotification(appNotification)
}

class DeleteNotificationUseCase(private val notificationRepository: NotificationsRepository) {
    suspend operator fun invoke(appNotification: AppNotification) =
        notificationRepository.deleteNotification(appNotification.id)
}

class FetchNotificationsUseCase(private val notificationRepository: NotificationsRepository) {
    suspend operator fun invoke() = notificationRepository.fetchNotifications()
}