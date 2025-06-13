package com.nca.yourdentist.domain.remote.repository

import com.nca.yourdentist.data.models.Report
import kotlinx.coroutines.flow.Flow

interface ReportsRepository {
    fun observeReports(): Flow<List<Report>>

    suspend fun updateReport(report: Report)
    suspend fun deleteReport(reportId: String)
    suspend fun rateDentist(dentistId: String, rate: Int)
}