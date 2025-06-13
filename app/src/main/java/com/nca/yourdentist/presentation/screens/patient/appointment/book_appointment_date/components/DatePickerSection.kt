package com.nca.yourdentist.presentation.screens.patient.appointment.book_appointment_date.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.component.ui.theme.white
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerSection(
    modifier: Modifier = Modifier,
    appointments: List<Appointment>,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {

    val availableDates = appointments
        .mapNotNull {
            it.timestamp?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
        }
        .distinct()
        .sorted()

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.date),
            style = AppTypography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = primaryLight
        )

        LazyRow(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(availableDates.size) { index ->
                val date = availableDates[index]
                val isSelected = (selectedDate == date)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSelected)
                                primaryLight.copy(alpha = 0.8f)
                            else
                                secondaryLight.copy(alpha = 0.5f)
                        )
                        .clickable { onDateSelected(date) }
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = date.dayOfWeek.name.take(3),
                        style = AppTypography.bodySmall,
                        color = white
                    )
                    Text(
                        text = date.dayOfMonth.toString(),
                        style = AppTypography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = white
                    )
                }
            }
        }

    }
}