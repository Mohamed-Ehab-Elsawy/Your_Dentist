package com.nca.yourdentist.presentation.screens.patient.reports.single_report_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.theme.onSurfaceLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight

@Composable
fun RatingSection(
    modifier: Modifier = Modifier,
    dentistName: String,
    rating: Int,
    onRatingSelected: (Int) -> Unit,
    onSubmitClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = surfaceLight),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Rate DR. Prof. $dentistName", color = onSurfaceLight)
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Star $i",
                        tint = if (i <= rating) Color(0xFFFFD700) else Color.Gray,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable {
                                onRatingSelected(i)
                            })
                }
            }
            CustomButton(text = stringResource(R.string.submit_rating), onClick = onSubmitClick)
        }
    }
}