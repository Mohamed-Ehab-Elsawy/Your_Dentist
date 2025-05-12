package com.nca.yourdentist.presentation.screens.dentist.home.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.models.AppointmentStatus
import com.nca.yourdentist.presentation.component.ui.NoContentSection
import com.nca.yourdentist.presentation.component.ui.ProgressDialog
import com.nca.yourdentist.presentation.screens.dentist.home.DentistHomeViewModel
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.toLocalDate
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentsSection(
    selectedDate: LocalDate,
    onAppointmentClick: (Appointment) -> Unit,
    vm: DentistHomeViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()

    var appointments by remember { mutableStateOf(emptyList<Appointment>()) }
    var dayAppointments by remember { mutableStateOf(emptyList<Appointment>()) }

    LaunchedEffect(Unit) { vm.fetchDentistAppointments() }

    LaunchedEffect(selectedDate) {
        dayAppointments = getAppointmentsForSingleDay(
            appointments,
            LocalDate.of(selectedDate.year, selectedDate.month, selectedDate.dayOfMonth)
        )
    }

    LazyColumn {
        if (dayAppointments.isNotEmpty()) items(dayAppointments.size) { index ->
            AppointmentInfoCard(
                appointment = dayAppointments[index],
                onAppointmentClick = { onAppointmentClick.invoke(it) }
            )
        }
        else item {
            Box(
                modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center
            ) {
                NoContentSection(
                    painter = R.drawable.img_no_appointments, title = stringResource(
                        R.string.no_appointments_today
                    )
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
                dayAppointments = getAppointmentsForSingleDay(
                    appointments,
                    LocalDate.of(selectedDate.year, selectedDate.month, selectedDate.dayOfMonth)
                )
            }
        }

        is UiState.Error -> {}
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getAppointmentsForSingleDay(
    appointments: List<Appointment>, selectedDate: LocalDate
): List<Appointment> = appointments.filter { appointment ->
    appointment.timestamp?.toLocalDate() == selectedDate
            && appointment.status.lowercase() == AppointmentStatus.BOOKED.name.lowercase()
}