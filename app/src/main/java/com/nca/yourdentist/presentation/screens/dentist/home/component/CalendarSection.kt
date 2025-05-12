package com.nca.yourdentist.presentation.screens.dentist.home.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight
import com.nca.yourdentist.presentation.component.ui.theme.white
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarSection(
    today: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {

    val maxDate = remember { today.plusMonths(1) }
    val allDates = remember { generateDates(today, maxDate) }
    val weeks = remember { allDates.chunked(5) }

    var currentWeekIndex by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = white),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            HeaderSection(
                selectedDate = selectedDate,
                currentWeekIndex = currentWeekIndex,
                onBack = { if (currentWeekIndex > 0) currentWeekIndex-- },
                onNext = { if (currentWeekIndex < weeks.size - 1) currentWeekIndex++ },
                today = today
            )

            WeekRow(
                weekDates = weeks[currentWeekIndex],
                selectedDate = selectedDate,
                onDateSelected = { onDateSelected.invoke(it) }
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeaderSection(
    selectedDate: LocalDate,
    currentWeekIndex: Int,
    onBack: () -> Unit,
    onNext: () -> Unit,
    today: LocalDate
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = selectedDate.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.ENGLISH)
            ),
            modifier = Modifier.weight(1f),
            style = AppTypography.titleLarge,
            color = primaryLight
        )
        IconButton(onClick = onBack) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.previous_week),
                tint = primaryLight
            )
        }
        IconButton(onClick = onNext) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(R.string.next_week),
                tint = primaryLight
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekRow(
    weekDates: List<LocalDate>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(weekDates.size) { dateIndex ->
            DayItem(
                date = weekDates[dateIndex],
                isSelected = weekDates[dateIndex] == selectedDate,
                onClick = { onDateSelected(weekDates[dateIndex]) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayItem(
    date: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) primaryLight else surfaceLight
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .width(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.dayOfWeek.name.take(3),
                style = AppTypography.bodyMedium,
                color = if (isSelected) white else primaryLight
            )
            Text(
                text = date.dayOfMonth.toString(),
                style = AppTypography.bodyLarge,
                color = if (isSelected) white else primaryLight
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateDates(start: LocalDate, end: LocalDate): List<LocalDate> {
    return generateSequence(start) { it.plusDays(1) }
        .takeWhile { !it.isAfter(end) }
        .toList()
}