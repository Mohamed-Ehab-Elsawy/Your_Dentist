package com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.appointment_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.data.models.Questionnaire
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight

@Composable
fun QuestionnaireTableSection(questionnaireResult: List<Questionnaire>) {

    Column(modifier = Modifier.padding(16.dp)) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(surfaceLight)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question",
                modifier = Modifier.weight(1f),
                style = AppTypography.labelLarge
            )
            Text(
                text = "Answer",
                modifier = Modifier.weight(0.25f),
                style = AppTypography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Data Rows
        questionnaireResult.forEach { question ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question.title,
                    modifier = Modifier.weight(1f),
                    style = AppTypography.bodyMedium
                )
                Text(
                    text = question.answer,
                    modifier = Modifier.weight(0.25f),
                    style = AppTypography.bodyMedium
                )
            }
        }
    }
}