package com.nca.yourdentist.data.remote.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.data.remote.ApiConstants
import com.nca.yourdentist.domain.remote.repository.NotificationsRepository
import com.nca.yourdentist.presentation.utils.AppProviders
import kotlinx.coroutines.tasks.await

class NotificationsRepositoryImpl(
    firestore: FirebaseFirestore
) : NotificationsRepository {

    private val notificationCollection =
        if (AppProviders.patient?.id != null)
            firestore.collection(ApiConstants.PATIENT_COLLECTIONS)
                .document(AppProviders.patient?.id!!)
                .collection(ApiConstants.NOTIFICATIONS)
        else
            firestore.collection(ApiConstants.DENTIST_COLLECTIONS)
                .document(AppProviders.dentist?.id!!)
                .collection(ApiConstants.NOTIFICATIONS)

    override suspend fun addNotification(appNotification: AppNotification) {
        notificationCollection.document(appNotification.id).set(appNotification).await()
    }

    override suspend fun updateNotification(appNotification: AppNotification) {
        notificationCollection.document(appNotification.id).set(appNotification).await()
    }

    override suspend fun fetchNotifications(): List<AppNotification> {
        val appNotifications = mutableListOf<AppNotification>()
        val querySnapshot = notificationCollection.get().await()
        for (document in querySnapshot.documents) {
            val appNotification = document.toObject(AppNotification::class.java)
            if (appNotification != null) {
                appNotifications.add(appNotification)
            }
        }
        return appNotifications
    }

    override suspend fun deleteNotification(notificationId: String) {
        notificationCollection.document(notificationId).delete().await()
    }

}