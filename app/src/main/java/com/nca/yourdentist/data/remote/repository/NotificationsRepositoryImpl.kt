package com.nca.yourdentist.data.remote.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.data.remote.utils.FirebaseConstants
import com.nca.yourdentist.domain.remote.repository.NotificationsRepository
import com.nca.yourdentist.presentation.utils.Provider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NotificationsRepositoryImpl(
    firestore: FirebaseFirestore
) : NotificationsRepository {

    private val notificationCollection =
        if (Provider.patient?.id != null)
            firestore.collection(FirebaseConstants.PATIENT_COLLECTIONS)
                .document(Provider.patient?.id!!)
                .collection(FirebaseConstants.NOTIFICATIONS)
        else
            firestore.collection(FirebaseConstants.DENTIST_COLLECTIONS)
                .document(Provider.dentist?.id!!)
                .collection(FirebaseConstants.NOTIFICATIONS)


    override suspend fun updateNotification(appNotification: AppNotification) {
        notificationCollection.document(appNotification.id).update(
            mapOf(
                "read" to appNotification.read,
                "notified" to appNotification.notified
            )
        ).await()
    }

    override fun observeNotifications(): Flow<List<AppNotification>> = callbackFlow {
        Log.e("NotificationsRepository", "Start listening to notifications...")
        val listener = notificationCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null && !snapshot.isEmpty) {
                val notifications = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(AppNotification::class.java)
                }.sortedByDescending { it.createdAt }

                trySend(notifications).isSuccess
            }
        }
        awaitClose {
            Log.e("NotificationsRepository", "Stopped listening to notifications.")
            listener.remove()
        }
    }


    override suspend fun deleteNotification(notificationId: String) {
        notificationCollection.document(notificationId).delete().await()
    }

}