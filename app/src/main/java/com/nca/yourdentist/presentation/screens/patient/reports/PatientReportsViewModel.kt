package com.nca.yourdentist.presentation.screens.patient.reports

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.data.notification.NotificationHelper
import com.nca.yourdentist.domain.remote.usecase.reports.DeleteReportUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.ObserveReportsUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.RateDentistUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.UpdateReportUseCase
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientReportsViewModel(
    private val observeReportsUseCase: ObserveReportsUseCase,
    private val updateReportUseCase: UpdateReportUseCase,
    private val deleteReportUseCase: DeleteReportUseCase,
    private val rateDentistUseCase: RateDentistUseCase,
    private val notificationHelper: NotificationHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Report>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Report>>> = _uiState

    init {
        observeReports()
    }

    private fun observeReports() {
        viewModelScope.launch {
            try {
                observeReportsUseCase.invoke().collect { reports ->
                    val updatedList = mutableListOf<Report>()
                    reports.forEach { report ->
                        if (!report.notified) {
                            notificationHelper.sendGeneralNotification(
                                title = "New Report",
                                message = "Your dentist just finished your report, let's check it now 🔥"
                            )
                            updateReport(
                                report = report.copy(notified = true)
                            )
                        }
                        Log.e("PatientReportsViewModel", "new report: $report")
                        updatedList.add(report)
                    }
                    _uiState.value = UiState.Success(updatedList)
                }
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    fun rateDentist(report: Report, rating: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                rateDentistUseCase.invoke(report.dentistId!!, rating)
                updateReport(report.copy(dentistRated = true))
                _uiState.value = UiState.Idle
                Log.e("PatientReportsViewModel", "rateDentist: $rating")
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
                Log.e("PatientReportsViewModel ERROR", "rateDentist: ${t.localizedMessage}")
            }
        }
    }

    fun updateReport(report: Report) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                updateReportUseCase.invoke(report)
                _uiState.value = UiState.Idle
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    fun deleteReport(report: Report) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                deleteReportUseCase.invoke(report)
                _uiState.value = UiState.Idle
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }
}