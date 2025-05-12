package com.nca.yourdentist.presentation.screens.patient.home.appointment.book_appointment_date.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.models.AppointmentStatus
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.component.ui.theme.white
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerSection(
    modifier: Modifier = Modifier,
    appointments: List<Appointment>,
    selectedDate: LocalDate,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current

    val timeFormatter = DateTimeFormatter.ofPattern("hh:mma", Locale.US)

    val timesForSelectedDate = appointments
        .filter {
            it.timestamp?.toDate()?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDate() == selectedDate
        }
        .mapNotNull { appointment ->
            val time = appointment.timestamp?.toDate()
                ?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalTime()

            time?.let {
                appointment to it.format(timeFormatter)
            }
        }.sortedBy { it.second }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            stringResource(R.string.time),
            style = AppTypography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = primaryLight
        )

        FlowRow(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 3
        ) {
            timesForSelectedDate.forEach { (appointment, timeStr) ->
                val isSelected = selectedTime == timeStr
                val isAvailable =
                    appointment.status.equals(AppointmentStatus.AVAILABLE.name, ignoreCase = true)


                Text(
                    text = timeStr,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            when {
                                !isAvailable -> errorLight.copy(alpha = 0.8f)
                                isSelected -> primaryLight.copy(alpha = 0.8f)
                                else -> secondaryLight.copy(alpha = 0.5f)
                            }
                        )
                        .clickable(enabled = isAvailable) {
                            if (isAvailable) {
                                onTimeSelected(timeStr)
                            } else {
                                Toast.makeText(
                                    context,
                                    "This time is already booked",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .padding(vertical = 12.dp, horizontal = 20.dp)
                        .width(70.dp),
                    textAlign = TextAlign.Center,
                    color = white,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}