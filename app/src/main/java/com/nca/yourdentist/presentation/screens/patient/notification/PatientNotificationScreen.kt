package com.nca.yourdentist.presentation.screens.patient.notification

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
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.presentation.component.ui.NoContentSection
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.screens.patient.notification.component.NotificationItem
import com.nca.yourdentist.presentation.utils.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun PatientNotificationScreen(
    navController: NavController,
    viewModel: PatientNotificationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    var appNotifications by remember { mutableStateOf<List<AppNotification>>(emptyList()) }

    var isRefreshing by remember { mutableStateOf(true) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    LaunchedEffect(Unit) {
        viewModel.fetchNotifications()
    }

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
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    isRefreshing = true
                    viewModel.fetchNotifications()
                }, modifier = Modifier.fillMaxSize(),
                swipeEnabled = true
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (appNotifications.isNotEmpty())
                        items(appNotifications.size) { index ->
                            NotificationItem(appNotification = appNotifications[index])
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
    }

    when (uiState) {

        is UiState.Success -> {
            LaunchedEffect(uiState) {
                isRefreshing = false
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