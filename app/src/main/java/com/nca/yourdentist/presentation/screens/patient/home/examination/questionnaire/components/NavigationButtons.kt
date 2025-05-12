package com.nca.yourdentist.presentation.screens.patient.home.examination.questionnaire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Question
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationButtons(
    pagerState: PagerState,
    questions: List<Question>,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    val currentQuestionAnswered by remember(questions, pagerState.currentPage) {
        derivedStateOf { questions[pagerState.currentPage].answer.isNotEmpty() }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Prev button
        if (pagerState.currentPage > 0) {
            OutlinedButton(
                onClick = { onPrevClick.invoke() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(id = R.string.previous),
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        // Next Button
        Button(
            onClick = {
                if (pagerState.currentPage < questions.size - 1)
                    onNextClick.invoke()
                else
                    onSubmitClick.invoke()
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(primaryLight)
            //enabled = currentQuestionAnswered
        ) {
            Text(
                text =
                    if (pagerState.currentPage == questions.size - 1) stringResource(id = R.string.submit)
                    else stringResource(id = R.string.next),
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}