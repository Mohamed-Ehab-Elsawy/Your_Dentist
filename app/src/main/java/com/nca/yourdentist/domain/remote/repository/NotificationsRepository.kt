package com.nca.yourdentist.domain.remote.repository

import com.nca.yourdentist.data.models.AppNotification
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {
    fun observeNotifications(): Flow<List<AppNotification>>

    suspend fun updateNotification(appNotification: AppNotification)
    suspend fun deleteNotification(notificationId: String)
}