package com.nca.yourdentist.presentation.screens.patient.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.domain.remote.usecase.notification.AddNotificationUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.DeleteNotificationUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.FetchNotificationsUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.UpdateNotificationUseCase
import com.nca.yourdentist.presentation.utils.AppProviders
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PatientNotificationViewModel(
    private val preferencesHelper: PreferencesHelper,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase,
    private val fetchNotificationsUseCase: FetchNotificationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<AppNotification>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<AppNotification>>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()

    fun addNotification() {
        viewModelScope.launch {
            try {
                val appNotification =
                    AppNotification(
                        title = "New notification",
                        body = "Hello, ${AppProviders.patient?.name} This is a new notification "
                    )
                addNotificationUseCase.invoke(appNotification)
            } catch (t: Throwable) {
                _snackBarMessage.emit("Error adding notification")
            }
        }
    }

    fun updateNotificationState(appNotification: AppNotification) {
        appNotification.isRead = true
        viewModelScope.launch {
            try {
                updateNotificationUseCase.invoke(appNotification)
            } catch (t: Throwable) {
                _snackBarMessage.emit("Error updating notification")
            }
        }
    }

    fun deleteNotification(appNotification: AppNotification) {
        viewModelScope.launch {
            try {
                deleteNotificationUseCase.invoke(appNotification)
            } catch (t: Throwable) {
                _snackBarMessage.emit("Error deleting notification")
            }
        }
    }

    fun fetchNotifications() {
        _uiState.value = UiState.Loading
        try {
            viewModelScope.launch {
                val notifications = fetchNotificationsUseCase.invoke()
                _uiState.value = UiState.Success(data = notifications)
            }
        } catch (t: Throwable) {
            _uiState.value =
                UiState.Error(t = t)
        }
    }

}