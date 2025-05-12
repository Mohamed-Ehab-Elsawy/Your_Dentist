package com.nca.yourdentist.domain.remote.repository

import com.nca.yourdentist.data.models.AppNotification

interface NotificationsRepository {

    suspend fun addNotification(appNotification: AppNotification)
    suspend fun updateNotification(appNotification: AppNotification)
    suspend fun deleteNotification(notificationId: String)

    suspend fun fetchNotifications(): List<AppNotification>
}