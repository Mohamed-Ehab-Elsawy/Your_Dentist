package com.nca.yourdentist.presentation.screens.patient.caries_detection.questionnaire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.R


@Composable
fun QuestionItem(
    question: String,
    initialAnswer: String = "",
    onAnswerSelected: (String) -> Unit
) {
    var selectedAnswer by remember { mutableStateOf(initialAnswer) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Question Text
        Text(
            text = question,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AnswerButton(
                text = stringResource(id = R.string.yes),
                isSelected = selectedAnswer == stringResource(id = R.string.yes),
                onClick = {
                    selectedAnswer = context.getString(R.string.yes)
                    onAnswerSelected(context.getString(R.string.yes))
                }
            )
            AnswerButton(
                text = stringResource(id = R.string.no),
                isSelected = selectedAnswer == stringResource(id = R.string.no),
                onClick = {
                    selectedAnswer = context.getString(R.string.no)
                    onAnswerSelected(context.getString(R.string.no))
                }
            )
        }
    }
}