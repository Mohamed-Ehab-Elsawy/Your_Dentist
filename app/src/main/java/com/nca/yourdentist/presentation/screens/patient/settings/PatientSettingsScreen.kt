package com.nca.yourdentist.presentation.screens.patient.settings

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.Screen
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import org.koin.androidx.compose.koinViewModel

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
fun PatientSettingsScreen(
    navController: NavController,
    viewModel: PatientSettingsViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val activity = context as? Activity
    Scaffold(
        topBar = {
            TopApplicationBar(
                title = (stringResource(id = R.string.settings)),
                iconRes = R.drawable.ic_arrow_left,
                iconTint = primaryLight, onIconClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsButton(
                text = stringResource(id = R.string.notification),
                icon = painterResource(id = R.drawable.ic_notification),
                onClick = {
                    navController.navigate(Screen.PatientNotificationSettings.route)
                }
            )
            SettingsButton(
                text = stringResource(id = R.string.change_language),
                icon = painterResource(id = R.drawable.ic_world),
                onClick = {
                    viewModel.changeLanguage()
                    activity?.recreate()
                }
            )
            SettingsButton(
                text = stringResource(id = R.string.contact_us),
                icon = painterResource(id = R.drawable.ic_phone),
                onClick = {
                    onContactUsClick(activity!!)
                }
            )
            SettingsButton(
                text = stringResource(id = R.string.about_us),
                icon = painterResource(id = R.drawable.ic_information),
                onClick = {
                    navController.navigate(Screen.PatientAboutUsSettings.route)
                }
            )
            SettingsButton(
                text = stringResource(id = R.string.log_out),
                icon = painterResource(id = R.drawable.ic_logout),
                onClick = {
                    viewModel.logout()
                    navController.navigate(Screen.SelectUserType.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

private fun onContactUsClick(activity: Activity) {
    try {
        val whatsappURL = "https://wa.me/+201142893996"
        val browserIntent = Intent(Intent.ACTION_VIEW, whatsappURL.toUri())
        activity.startActivity(browserIntent)
    } catch (_: Throwable) {
        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
    }
}