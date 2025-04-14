package com.nca.yourdentist.presentation.screens.patient.settings

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.nca.yourdentist.MainActivity
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.screens.patient.settings.component.AboutUsButton
import com.nca.yourdentist.presentation.screens.patient.settings.component.ChangeLanguageButton
import com.nca.yourdentist.presentation.screens.patient.settings.component.ContactUsButton
import com.nca.yourdentist.presentation.screens.patient.settings.component.LogoutButton
import com.nca.yourdentist.presentation.screens.patient.settings.component.NotificationsSettingsButton
import com.nca.yourdentist.utils.Constant
import org.koin.androidx.compose.koinViewModel

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
                title = (stringResource(id = R.string.settings))
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_background),
                contentDescription = stringResource(R.string.background),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                NotificationsSettingsButton {
                    navController.navigate(PatientScreens.NotificationSettings.route)
                }

                ChangeLanguageButton {
                    viewModel.changeLanguage()
                    activity?.recreate()
                }

                ContactUsButton {
                    val whatsappURL = "https://wa.me/+201142893996"
                    val browserIntent = Intent(Intent.ACTION_VIEW, whatsappURL.toUri())
                    activity?.startActivity(browserIntent)
                }

                AboutUsButton {
                    navController.navigate(PatientScreens.AboutUsSettings.route)
                }

                LogoutButton {
                    viewModel.logout()
                    activity?.startActivity(
                        Intent(context, MainActivity::class.java).apply {
                            putExtra(Constant.AUTH_SCREEN, true)
                        }
                    )
                    activity?.finish()
                }

            }
        }
    }
}