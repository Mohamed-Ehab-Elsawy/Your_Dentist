package com.nca.yourdentist.presentation.component.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onSurfaceLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceLight

@Composable
fun NoInternetDialog() {
    AlertDialog(
        onDismissRequest = { /* Prevent dismissal */ },
        confirmButton = { /* No button needed */ },
        containerColor = surfaceLight,
        titleContentColor = errorLight,
        textContentColor = onSurfaceLight,
        title = { Text(text = stringResource(R.string.no_internet_connection)) },
        text = { Text(text = stringResource(R.string.check_your_internet_connection)) },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_no_internet),
                contentDescription = stringResource(R.string.no_internet_connection)
            )
        },
        iconContentColor = errorLight
    )
}