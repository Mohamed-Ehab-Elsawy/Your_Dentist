package com.nca.yourdentist.domain.remote.repository

import com.nca.yourdentist.data.models.Report

interface ReportsRepository {

    suspend fun addReport(report: Report)
    suspend fun updateReport(report: Report)
    suspend fun deleteReport(reportId: String)

    suspend fun fetchReports(): List<Report>

}