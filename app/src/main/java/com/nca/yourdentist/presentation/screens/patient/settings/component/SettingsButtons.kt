package com.nca.yourdentist.presentation.screens.patient.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight

@Composable
fun SettingsButton(text: String, icon: Painter, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = onPrimaryLight,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = onPrimaryLight,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun NotificationsSettingsButton(onClick: () -> Unit) {
    SettingsButton(
        text = stringResource(id = R.string.notification_settings),
        icon = painterResource(id = R.drawable.ic_notification),
        onClick = { onClick.invoke() }
    )
}

@Composable
fun ChangeLanguageButton(onClick: () -> Unit) {
    SettingsButton(
        text = stringResource(id = R.string.change_language),
        icon = painterResource(id = R.drawable.ic_world),
        onClick = { onClick.invoke() }
    )
}

@Composable
fun ContactUsButton(onClick: () -> Unit) {
    SettingsButton(
        text = stringResource(id = R.string.contact_us),
        icon = painterResource(id = R.drawable.ic_phone),
        onClick = { onClick.invoke() }
    )
}

@Composable
fun AboutUsButton(onClick: () -> Unit) {
    SettingsButton(
        text = stringResource(id = R.string.about_us),
        icon = painterResource(id = R.drawable.ic_information),
        onClick = { onClick.invoke() }
    )
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    SettingsButton(
        text = stringResource(id = R.string.log_out),
        icon = painterResource(id = R.drawable.ic_logout),
        onClick = { onClick.invoke() }
    )
}