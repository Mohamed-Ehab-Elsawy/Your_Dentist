package com.nca.yourdentist.presentation.screens.patient.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@Composable
fun UpcomingAppointmentSection(
    modifier: Modifier = Modifier, patient: Patient, onClick: () -> Unit
) {
    if (!patient.upcomingAppointment?.id.isNullOrEmpty()) {
        Row(
            modifier
                .fillMaxWidth()
                .clickable { onClick.invoke() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.upcoming_appointment),
                color = primaryLight,
                style = AppTypography.headlineSmall
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = primaryLight
            )
        }
    }
}