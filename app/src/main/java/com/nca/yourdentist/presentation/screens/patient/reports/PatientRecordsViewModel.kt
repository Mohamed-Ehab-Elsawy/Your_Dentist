package com.nca.yourdentist.presentation.screens.patient.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.domain.remote.usecase.reports.AddReportUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.DeleteReportUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.FetchReportsUseCase
import com.nca.yourdentist.domain.remote.usecase.reports.UpdateReportUseCase
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientRecordsViewModel(
    private val addReportUseCase: AddReportUseCase,
    private val fetchReportsUseCase: FetchReportsUseCase,
    private val updateReportUseCase: UpdateReportUseCase,
    private val deleteReportUseCase: DeleteReportUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Report>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Report>>> = _uiState

    fun addReport(report: Report) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                addReportUseCase.invoke(report)
                _uiState.value = UiState.Idle
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
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

    fun fetchReports() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val reports = fetchReportsUseCase.invoke()
                _uiState.value = UiState.Success(reports)
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

}