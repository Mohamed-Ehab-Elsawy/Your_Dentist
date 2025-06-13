package com.nca.yourdentist.presentation.screens.patient.home

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.customized.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.errorContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorContainerLight
import com.nca.yourdentist.presentation.screens.patient.home.component.CheckUpButton
import com.nca.yourdentist.presentation.screens.patient.home.component.PagerSection
import com.nca.yourdentist.presentation.screens.patient.home.component.QRCodeDialog
import com.nca.yourdentist.presentation.screens.patient.home.component.WelcomeSection
import com.nca.yourdentist.presentation.utils.Provider
import com.nca.yourdentist.presentation.utils.UiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PatientHomeScreen(
    navController: NavController,
    homeVM: PatientHomeViewModel = koinViewModel()
) {

    val uiState by homeVM.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    var patient by remember { mutableStateOf(Patient()) }

    if (showDialog) {
        QRCodeDialog { showDialog = false }
    }

    LaunchedEffect(Unit) {
        patient = homeVM.fetchPatient()
        homeVM.scheduleToothbrushReminder()
    }

    Scaffold(
        topBar = {
            TopApplicationBar(title = stringResource(R.string.home))
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = errorContainerLight,
                    contentColor = onErrorContainerLight
                )
            }
        }
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top
            ) {

                item { WelcomeSection(patientName = patient.name ?: "") { showDialog = true } }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item { PagerSection() }

                item { Spacer(modifier = Modifier.height(32.dp)) }
                item {

                    CheckUpButton {
                        navController.navigate(PatientScreens.Camera.route)
                    }
                }
            }
        }
        when (uiState) {
            is UiState.Loading -> ProgressDialog()

            is UiState.Success -> {
                val patientQrCodeBitmap = (uiState as UiState.Success<Bitmap>).data
                Provider.patientQRCodeBitmap = patientQrCodeBitmap
            }

            is UiState.Error -> {
                val errorMessage = (uiState as UiState.Error).t.localizedMessage
                LaunchedEffect(Unit) {
                    snackBarHostState.showSnackbar(errorMessage ?: "")
                }
            }

            is UiState.Idle -> {}
        }
    }
}