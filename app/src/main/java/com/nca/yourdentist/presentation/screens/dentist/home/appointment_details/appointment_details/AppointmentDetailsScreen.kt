package com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.appointment_details

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.navigation.DentistScreens
import com.nca.yourdentist.presentation.component.ui.customized.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.`X-RaysSection`
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryContainerLight
import com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.appointment_details.components.NotesSection
import com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.appointment_details.components.QuestionnaireTableSection
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppointmentDetailsScreen(
    navController: NavController, vm: AppointmentDetailsViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val appointment =
        navController.previousBackStackEntry?.savedStateHandle?.get<Appointment>(Constant.APPOINTMENT)
            ?: Appointment()
    val questionnaireResult = appointment.report?.questionnaire

    val snackBarHostState = remember { SnackbarHostState() }
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopApplicationBar(title = stringResource(R.string.patient_report)) },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = primaryContainerLight,
                    contentColor = onPrimaryContainerLight
                )
            }
        }) { paddingValues ->
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
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { QuestionnaireTableSection(questionnaireResult ?: emptyList()) }
                item { Spacer(Modifier.height(16.dp)) }
                item { `X-RaysSection`(report = appointment.report ?: Report()) }
                item { Spacer(Modifier.height(16.dp)) }
                item {
                    NotesSection(
                        value = notes, onValueChange = { notes = it },
                        onSubmitClick = { vm.submitNotes(appointment, notes) }
                    )
                }
            }
        }
    }
    when (uiState) {
        is UiState.Idle -> {}
        is UiState.Loading -> ProgressDialog()
        is UiState.Success -> {
            navController.navigate(DentistScreens.Home.route) {
                popUpTo(DentistScreens.Home.route) {
                    inclusive = true
                }
            }
        }

        is UiState.Error -> {
            LaunchedEffect(true) {
                snackBarHostState.showSnackbar((uiState as UiState.Error).t.localizedMessage ?: "")
            }
        }
    }
}