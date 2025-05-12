package com.nca.yourdentist.presentation.screens.patient.home.appointment.book_appointment_date

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.domain.models.AppointmentBookResponse
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.theme.errorContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.white
import com.nca.yourdentist.presentation.screens.patient.home.appointment.book_appointment_date.components.DatePickerSection
import com.nca.yourdentist.presentation.screens.patient.home.appointment.book_appointment_date.components.ProfileSection
import com.nca.yourdentist.presentation.screens.patient.home.appointment.book_appointment_date.components.TimePickerSection
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookAppointmentScreen(
    navController: NavController,
    vm: BookAppointmentViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val appointmentsUiState by vm.appointmentsUiState.collectAsState()
    val bookingUiState by vm.bookingUiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf("") }
    var appointments by remember { mutableStateOf(listOf(Appointment())) }


    val dentist =
        navController.previousBackStackEntry?.savedStateHandle?.get<Dentist>(Constant.DENTIST)
            ?: Dentist()

    LaunchedEffect(dentist) { vm.fetchDentistAppointments(dentist.id!!) }

    Scaffold(
        topBar = {
            TopApplicationBar(
                title = stringResource(R.string.book_appointment),
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                iconTint = primaryLight,
                onIconClick = { navController.popBackStack() }
            )
        }, snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = errorContainerLight,
                    contentColor = onErrorContainerLight
                )
            }

        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(color = white)
        ) {
            item { ProfileSection(dentist) }

            item {
                DatePickerSection(
                    modifier = Modifier.padding(top = 16.dp),
                    appointments = appointments,
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )
            }

            item {
                TimePickerSection(
                    modifier = Modifier.padding(top = 32.dp),
                    appointments = appointments,
                    selectedTime = selectedTime, selectedDate = selectedDate,
                    onTimeSelected = { selectedTime = it }
                )
            }

            item {
                // Book now button
                CustomButton(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(R.string.book_now),
                    enabled = !selectedTime.isEmpty(),
                    onClick = {
                        val selectedAppointment =
                            findSelectedAppointment(appointments, selectedDate, selectedTime)
                        Log.e(
                            "BookAppointmentScreen",
                            "Selected Appointment: ${selectedAppointment?.copy()}"
                        )
                        selectedAppointment?.let { vm.bookAppointment(it) }
                    }
                )
            }
        }
    }
    when (appointmentsUiState) {
        is UiState.Idle -> {}

        is UiState.Loading -> ProgressDialog()

        is UiState.Success -> {
            appointments = (appointmentsUiState as UiState.Success<List<Appointment>>).data
            Log.e("BookAppointmentScreen", "Appointments: ${appointments.size}")
        }

        is UiState.Error -> {
            //TODO: Handle error state
        }
    }

    when (bookingUiState) {
        is UiState.Idle -> {}

        is UiState.Loading -> ProgressDialog()

        is UiState.Success -> {
            Toast.makeText(
                context,
                AppointmentBookResponse.BOOKED_SUCCESSFULLY.name.lowercase(),
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate(PatientScreens.Home.route) {
                popUpTo(PatientScreens.Home.route) {
                    inclusive = true
                }
            }
        }

        is UiState.Error -> {
            val errorThrowable = (bookingUiState as UiState.Error).t
            when (errorThrowable.message) {
                AppointmentBookResponse.BOOKED_ALREADY.name.lowercase() -> {
                    LaunchedEffect(Unit) {
                        snackBarHostState.showSnackbar(context.getString(R.string.already_booked))
                    }
                }

                AppointmentBookResponse.APPOINTMENT_NOT_FOUND.name.lowercase() -> {
                    LaunchedEffect(Unit) {
                        snackBarHostState.showSnackbar(context.getString(R.string.appointment_not_found))
                    }
                }

                else -> {
                    LaunchedEffect(Unit) {
                        snackBarHostState.showSnackbar(errorThrowable.localizedMessage ?: "")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun findSelectedAppointment(
    appointments: List<Appointment>,
    selectedDate: LocalDate,
    selectedTime: String
): Appointment? = appointments.firstOrNull { appointment ->
    appointment.timestamp?.toDate()?.let { date ->
        val formatter = DateTimeFormatter.ofPattern("hh:mma", Locale.ENGLISH)

        val localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        val appointmentDate = localDateTime.toLocalDate()
        val appointmentTime = localDateTime.toLocalTime().format(formatter)

        appointmentDate == selectedDate && appointmentTime == selectedTime
    } == true
}