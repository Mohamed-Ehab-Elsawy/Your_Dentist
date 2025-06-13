package com.nca.yourdentist.presentation.screens.patient.caries_detection.results.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@Composable
fun BookNowSection(
    enabled: Boolean,
    onBookNowClick: () -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.book_now_description),
            color = primaryLight,
            style = AppTypography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = stringResource(R.string.book_now),
            enabled = enabled,
            onClick = onBookNowClick
        )
    }
}