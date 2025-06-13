package com.nca.yourdentist.presentation.screens.patient.caries_detection.questionnaire.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.R


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PageNumberIndicator(currentPage: Int, totalPages: Int) {
    AnimatedContent(
        targetState = currentPage,
        transitionSpec = {
            fadeIn(animationSpec = tween(200)) togetherWith fadeOut(animationSpec = tween(200))
        },
        label = stringResource(R.string.page_number_animation)
    ) { animatedPageNumber ->
        Row {
            Text(
                text = "$animatedPageNumber",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " / $totalPages",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}