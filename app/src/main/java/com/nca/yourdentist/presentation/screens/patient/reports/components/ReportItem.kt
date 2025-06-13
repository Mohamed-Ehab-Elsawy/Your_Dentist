package com.nca.yourdentist.presentation.screens.patient.reports.components

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight
import com.nca.yourdentist.utils.toFormattedString

@Composable
fun ReportItem(
    modifier: Modifier = Modifier,
    report: Report,
    onClick: (Report) -> Unit
) {
    var formattedDate by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        formattedDate = report.creationTime.toFormattedString()
    }

    Card(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceLight
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick.invoke(report) }
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
                    tint = primaryLight
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(R.string.report),
                        style = AppTypography.titleMedium,
                        modifier = modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        color = primaryLight
                    )
                    Text(
                        stringResource(R.string.date_) + " " + formattedDate,
                        style = AppTypography.bodyLarge,
                        color = secondaryLight
                    )
                }
            }
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