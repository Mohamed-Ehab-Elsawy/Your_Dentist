package com.nca.yourdentist.data.remote.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.data.remote.ApiConstants
import com.nca.yourdentist.domain.remote.repository.ReportsRepository
import com.nca.yourdentist.presentation.utils.AppProviders
import kotlinx.coroutines.tasks.await

class ReportsRepositoryImpl(
    firestore: FirebaseFirestore
) : ReportsRepository {

    private val reportCollection =
        firestore.collection(ApiConstants.PATIENT_COLLECTIONS).document(AppProviders.patient?.id!!)
            .collection(ApiConstants.REPORTS)

    override suspend fun addReport(report: Report) {
        reportCollection.add(report).await()
    }

    override suspend fun updateReport(report: Report) {
        reportCollection.document(report.id).set(report).await()
    }

    override suspend fun deleteReport(reportId: String) {
        reportCollection.document(reportId).delete().await()
    }

    override suspend fun fetchReports(): List<Report> {
        val reports = mutableListOf<Report>()
        val querySnapshot = reportCollection.get().await()
        for (document in querySnapshot.documents) {
            val report = document.toObject(Report::class.java)
            if (report != null) {
                reports.add(report)
            }
        }
        return reports
    }

}