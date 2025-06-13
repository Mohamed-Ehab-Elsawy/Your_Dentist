package com.nca.yourdentist.presentation.screens.dentist.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.presentation.component.ui.customized.NoContentSection
import com.nca.yourdentist.presentation.component.ui.customized.NotificationItem
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.utils.UiState

@Composable
fun DentistNotificationScreen(
    vm: DentistNotificationViewModel
) {
    val uiState by vm.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    var appNotifications by remember { mutableStateOf<List<AppNotification>>(emptyList()) }

    Scaffold(
        topBar = { TopApplicationBar(title = stringResource(R.string.notification)) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_background),
                contentDescription = stringResource(R.string.background),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (appNotifications.isNotEmpty())
                    items(appNotifications.size) { index ->
                        NotificationItem(
                            appNotification = appNotifications[index],
                            onNotificationClick = { vm.updateNotificationState(it.copy(read = true)) }
                        )
                    }
                else
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        )
                        {
                            NoContentSection(
                                modifier = Modifier.wrapContentSize(),
                                painter = R.drawable.img_no_notifications,
                                title = stringResource(R.string.no_notifications_yet)
                            )
                        }
                    }
            }
        }
    }

    when (uiState) {

        is UiState.Success -> {
            LaunchedEffect(uiState) {
                val newList = (uiState as UiState.Success<List<AppNotification>>).data
                if (newList.isNotEmpty()) appNotifications = newList
            }
        }

        is UiState.Error -> {
            LaunchedEffect(uiState) {
                snackBarHostState.showSnackbar(
                    (uiState as UiState.Error).t.localizedMessage ?: "Error occurred"
                )
            }
        }

        else -> {}
    }
}