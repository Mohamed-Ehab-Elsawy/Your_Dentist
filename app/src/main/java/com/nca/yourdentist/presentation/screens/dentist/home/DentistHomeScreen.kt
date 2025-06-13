package com.nca.yourdentist.presentation.screens.dentist.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.models.AppointmentStatus.BOOKED
import com.nca.yourdentist.domain.models.AppointmentStatus.COMPLETED
import com.nca.yourdentist.navigation.DentistScreens
import com.nca.yourdentist.presentation.component.ui.customized.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.screens.dentist.home.component.AppointmentsSection
import com.nca.yourdentist.presentation.screens.dentist.home.component.CalendarSection
import com.nca.yourdentist.presentation.utils.Provider
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.capitalizeFirstLetter
import com.nca.yourdentist.utils.toLocalDate
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DentistHomeScreen(
    navController: NavController,
    vm: DentistHomeViewModel = koinViewModel()
) {

    val uiState by vm.uiState.collectAsState()
    val today = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(today) }
    var appointments by remember { mutableStateOf(emptyList<Appointment>()) }
    var dayAppointments by remember { mutableStateOf(emptyList<Appointment>()) }

    LaunchedEffect(Unit) { vm.fetchDentistAppointments() }

    LaunchedEffect(selectedDate) {
        dayAppointments = getAppointmentsForSingleDay(
            appointments,
            LocalDate.of(selectedDate.year, selectedDate.month, selectedDate.dayOfMonth)
        )
    }

    Scaffold(
        topBar = {
            TopApplicationBar(title = stringResource(R.string.home))
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
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.welcome_dr) + " " +
                            Provider.dentist?.name!!.capitalizeFirstLetter(),
                    style = AppTypography.headlineSmall
                )

                Spacer(Modifier.height(8.dp))

                CalendarSection(
                    today = today,
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )

                Spacer(Modifier.height(16.dp))

                AppointmentsSection(
                    selectedDate = selectedDate,
                    dayAppointments = dayAppointments,
                    onAppointmentClick = { appointment ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            Constant.APPOINTMENT,
                            appointment
                        )
                        navController.navigate(DentistScreens.ScanQRCodeScreen.route)
                    }
                )
            }
        }
    }
    when (uiState) {

        is UiState.Idle -> {}

        is UiState.Loading -> ProgressDialog()

        is UiState.Success -> {
            LaunchedEffect(uiState) {
                val newList = (uiState as UiState.Success<List<Appointment>>).data
                appointments = if (!newList.isEmpty()) newList
                else emptyList()
                Log.e("AppointmentsSection", "appointments: $appointments")
                dayAppointments = getAppointmentsForSingleDay(
                    appointments,
                    LocalDate.of(selectedDate.year, selectedDate.month, selectedDate.dayOfMonth)
                )
            }
        }

        is UiState.Error -> {
            LaunchedEffect(Unit) {
                val errorThrowable = (uiState as UiState.Error).t
                Log.e("AppointmentsSection", "error: ${errorThrowable.message}")
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun getAppointmentsForSingleDay(
    appointments: List<Appointment>, selectedDate: LocalDate
): List<Appointment> = appointments.filter { appointment ->
    appointment.timestamp?.toLocalDate() == selectedDate
            && (
            appointment.status.lowercase() == BOOKED.name.lowercase()
                    || appointment.status.lowercase() == COMPLETED.name.lowercase()
            )
}