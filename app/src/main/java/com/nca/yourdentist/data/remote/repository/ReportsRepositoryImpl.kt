package com.nca.yourdentist.data.remote.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.remote.utils.FirebaseConstants
import com.nca.yourdentist.domain.remote.repository.ReportsRepository
import com.nca.yourdentist.presentation.utils.Provider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ReportsRepositoryImpl(
    firestore: FirebaseFirestore
) : ReportsRepository {

    private val reportCollection =
        firestore.collection(FirebaseConstants.PATIENT_COLLECTIONS)
            .document(Provider.patient?.id!!)
            .collection(FirebaseConstants.REPORTS)
    private val dentistCollection = firestore.collection(FirebaseConstants.DENTIST_COLLECTIONS)

    override fun observeReports(): Flow<List<Report>> = callbackFlow {
        Log.e("ReportsRepository", "Start listening to reports...")
        val listener = reportCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null && !snapshot.isEmpty) {
                val reports = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Report::class.java)
                }.sortedByDescending { it.creationTime }

                trySend(reports).isSuccess
            }
        }
        awaitClose {
            Log.e("ReportsRepository", "Stopped listening to reports.")
            listener.remove()
        }
    }

    override suspend fun updateReport(report: Report) {
        Log.e("ReportsRepository", "Updating report: ${report.id}")
        reportCollection.document(report.id!!).update(
            mapOf(
                "dentistRated" to report.dentistRated,
                "notified" to report.notified
            )
        )
        Log.e("ReportsRepository", "Report updated: ${report.id}")
    }

    override suspend fun deleteReport(reportId: String) {
        reportCollection.document(reportId).delete().await()
    }

    override suspend fun rateDentist(dentistId: String, rate: Int) {
        val dentistRef = dentistCollection.document(dentistId)
        val dentist = dentistRef.get().await().toObject(Dentist::class.java)!!
        val newRate =
            (dentist.rate.toDouble() + rate.toDouble()) / (dentist.patientsNumber).toDouble()
        dentistRef.update("rate", newRate).await()
    }

}