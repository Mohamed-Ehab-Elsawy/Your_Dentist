package com.nca.yourdentist.presentation.component.ui.customized.settingsButtons

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nca.yourdentist.R

@Composable
fun AboutUsButton(onClick: () -> Unit) {
    SettingsButton(
        text = stringResource(id = R.string.about_us),
        icon = painterResource(id = R.drawable.ic_information),
        onClick = { onClick.invoke() }
    )
}