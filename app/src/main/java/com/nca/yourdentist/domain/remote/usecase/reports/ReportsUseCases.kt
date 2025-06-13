package com.nca.yourdentist.domain.remote.usecase.reports

import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.domain.remote.repository.ReportsRepository
import kotlinx.coroutines.flow.Flow

class ObserveReportsUseCase(private val reportsRepository: ReportsRepository) {
    operator fun invoke(): Flow<List<Report>> = reportsRepository.observeReports()
}

class UpdateReportUseCase(private val reportsRepository: ReportsRepository) {
    suspend operator fun invoke(report: Report) =
        reportsRepository.updateReport(report)
}

class DeleteReportUseCase(private val reportsRepository: ReportsRepository) {
    suspend operator fun invoke(report: Report) =
        reportsRepository.deleteReport(report.id!!)
}

class RateDentistUseCase(private val reportsRepository: ReportsRepository) {
    suspend operator fun invoke(dentistId: String, rate: Int) =
        reportsRepository.rateDentist(dentistId, rate)
}