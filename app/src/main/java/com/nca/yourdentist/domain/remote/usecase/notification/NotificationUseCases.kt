package com.nca.yourdentist.domain.remote.usecase.notification

import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.domain.remote.repository.NotificationsRepository
import kotlinx.coroutines.flow.Flow

class UpdateNotificationUseCase(private val notificationRepository: NotificationsRepository) {
    suspend operator fun invoke(appNotification: AppNotification) =
        notificationRepository.updateNotification(appNotification)
}

class DeleteNotificationUseCase(private val notificationRepository: NotificationsRepository) {
    suspend operator fun invoke(appNotification: AppNotification) =
        notificationRepository.deleteNotification(appNotification.id)
}

class ObserveNotificationsUseCase(private val notificationRepository: NotificationsRepository) {
    suspend operator fun invoke(): Flow<List<AppNotification>> =
        notificationRepository.observeNotifications()
}