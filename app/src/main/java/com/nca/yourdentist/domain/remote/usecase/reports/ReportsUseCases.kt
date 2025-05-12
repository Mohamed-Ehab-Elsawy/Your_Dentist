package com.nca.yourdentist.domain.remote.usecase.reports

import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.domain.remote.repository.ReportsRepository

class AddReportUseCase(private val reportsRepository: ReportsRepository) {
    suspend operator fun invoke(report: Report) =
        reportsRepository.addReport(report)
}

class UpdateReportUseCase(private val reportsRepository: ReportsRepository) {
    suspend operator fun invoke(report: Report) =
        reportsRepository.updateReport(report)
}

class DeleteReportUseCase(private val reportsRepository: ReportsRepository) {
    suspend operator fun invoke(report: Report) =
        reportsRepository.deleteReport(report.id)
}

class FetchReportsUseCase(private val reportsRepository: ReportsRepository) {
    suspend operator fun invoke() = reportsRepository.fetchReports()
}