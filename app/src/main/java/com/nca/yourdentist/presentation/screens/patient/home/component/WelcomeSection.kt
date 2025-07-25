package com.nca.yourdentist.presentation.screens.patient.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.utils.capitalizeFirstLetter

@Composable
fun WelcomeSection(
    modifier: Modifier = Modifier,
    patientName: String,
    onIconClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.welcome) + patientName.capitalizeFirstLetter(),
            style = AppTypography.headlineSmall
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_qr_code),
            contentDescription = stringResource(R.string.qr_code_icon),
            tint = primaryLight,
            modifier = Modifier.clickable { onIconClick.invoke() }
        )
    }
}