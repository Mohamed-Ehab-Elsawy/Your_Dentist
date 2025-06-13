package com.nca.yourdentist.presentation.screens.dentist.home.component

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.models.AppointmentStatus.BOOKED
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.greenLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceContainerLight
import com.nca.yourdentist.presentation.component.ui.theme.white
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentInfoCard(
    modifier: Modifier = Modifier, appointment: Appointment,
    onAppointmentClick: (Appointment) -> Unit
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        .withZone(ZoneId.systemDefault())

    val instant = appointment.timestamp?.toDate()?.toInstant()
    val time = formatter.format(instant)
    Card(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                if (appointment.status.lowercase() == BOOKED.name.lowercase()) surfaceContainerLight
                else greenLight
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    if (appointment.status.lowercase() == BOOKED.name.lowercase())
                        onAppointmentClick.invoke(appointment)
                    else
                        Toast.makeText(
                            context,
                            context.getString(R.string.appointment_completed_already),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                }
        ) {
            Row(
                modifier = modifier
                    .wrapContentSize()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_reports_fill),
                    modifier = modifier
                        .padding(8.dp)
                        .size(54.dp),
                    contentDescription = null,
                    tint = if (appointment.status.lowercase() == BOOKED.name.lowercase()) primaryLight
                    else white
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Upcoming appointment",
                        style = AppTypography.titleMedium,
                        modifier = modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        color = if (appointment.status.lowercase() == BOOKED.name.lowercase()) primaryLight
                        else white
                    )
                    Text(
                        text = appointment.patientName!!,
                        style = AppTypography.bodyLarge,
                        color = if (appointment.status.lowercase() == BOOKED.name.lowercase()) secondaryLight
                        else white
                    )
                    Text(
                        text = time,
                        style = AppTypography.bodyLarge,
                        color = if (appointment.status.lowercase() == BOOKED.name.lowercase()) secondaryLight
                        else white
                    )
                }
            }
            if (appointment.status.lowercase() == BOOKED.name.lowercase())
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.expand_notification_arrow),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
                    tint = primaryLight
                )
        }
    }
}