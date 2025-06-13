package com.nca.yourdentist.presentation.screens.dentist.home.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.models.AppointmentStatus.BOOKED
import com.nca.yourdentist.domain.models.AppointmentStatus.COMPLETED
import com.nca.yourdentist.presentation.component.ui.customized.NoContentSection
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentsSection(
    selectedDate: LocalDate,
    dayAppointments: List<Appointment>,
    onAppointmentClick: (Appointment) -> Unit
) {
    LocalContext.current

    val sortedAppointments = dayAppointments.sortedWith(
        compareBy<Appointment> { appointment ->
            when (appointment.status.lowercase()) {
                BOOKED.name.lowercase() -> 0
                COMPLETED.name.lowercase() -> 1
                else -> 2
            }
        }.thenBy { it.timestamp }
    )

    LazyColumn {
        if (sortedAppointments.isNotEmpty()) items(sortedAppointments.size) { index ->
            AppointmentInfoCard(
                appointment = sortedAppointments[index],
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
}