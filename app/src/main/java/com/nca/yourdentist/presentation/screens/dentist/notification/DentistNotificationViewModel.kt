package com.nca.yourdentist.presentation.screens.dentist.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.data.notification.NotificationHelper
import com.nca.yourdentist.domain.remote.usecase.notification.DeleteNotificationUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.ObserveNotificationsUseCase
import com.nca.yourdentist.domain.remote.usecase.notification.UpdateNotificationUseCase
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DentistNotificationViewModel(
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase,
    private val observeNotificationsUseCase: ObserveNotificationsUseCase,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<AppNotification>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<AppNotification>>> = _uiState

    init {
        observeNotifications()
    }

    fun updateNotificationState(appNotification: AppNotification) {
        viewModelScope.launch {
            try {
                updateNotificationUseCase.invoke(appNotification)
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    fun deleteNotification(appNotification: AppNotification) {
        viewModelScope.launch {
            try {
                deleteNotificationUseCase.invoke(appNotification)
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    private fun observeNotifications() {
        viewModelScope.launch {
            try {
                observeNotificationsUseCase.invoke().collect { notifications ->

                    val updatedList = mutableListOf<AppNotification>()
                    notifications.forEach { notification ->
                        if (!notification.notified) {
                            try {
                                notificationHelper.sendGeneralNotification(
                                    title = notification.title,
                                    message = notification.body
                                )
                                updateNotificationState(
                                    appNotification = notification.copy(notified = true)
                                )
                                updatedList.add(notification.copy(notified = true))
                            } catch (e: Exception) {
                                Log.e(
                                    "NotificationError",
                                    "Failed to send or update: ${notification.id}"
                                )
                                updatedList.add(notification)
                            }
                        } else {
                            updatedList.add(notification)
                        }
                    }

                    _uiState.value = UiState.Success(updatedList)
                }
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

}