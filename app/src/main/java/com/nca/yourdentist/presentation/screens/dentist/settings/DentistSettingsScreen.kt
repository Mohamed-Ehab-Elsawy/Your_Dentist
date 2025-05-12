package com.nca.yourdentist.presentation.screens.dentist.settings

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.nca.yourdentist.presentation.component.ui.customized.settingsButtons.AboutUsButton
import com.nca.yourdentist.presentation.component.ui.customized.settingsButtons.ChangeLanguageButton
import com.nca.yourdentist.presentation.component.ui.customized.settingsButtons.ContactUsButton
import com.nca.yourdentist.presentation.component.ui.customized.settingsButtons.LogoutButton
import com.nca.yourdentist.presentation.component.ui.customized.settingsButtons.NotificationToggleButton
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.utils.Constant
import org.koin.androidx.compose.koinViewModel

@Composable
fun DentistSettingsScreen(
    navController: NavController,
    vm: DentistSettingsViewModel = koinViewModel()
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
                    .padding(horizontal = 20.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.app_logo),
                    modifier = Modifier
                        .size(150.dp)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    tint = primaryLight
                )

                NotificationToggleButton(
                    isEnable = vm.notificationsEnabled.value,
                    onCheckedChange = { vm.toggleNotifications() }
                )

                ChangeLanguageButton {
                    vm.changeLanguage()
                    activity?.recreate()
                }

                AboutUsButton {
                    navController.navigate(PatientScreens.AboutUsSettings.route)
                }

                ContactUsButton {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "mailto:yourdentist.cs@gmail.com".toUri()
                        putExtra(Intent.EXTRA_SUBJECT, "Support Request")
                        putExtra(Intent.EXTRA_TEXT, "Hello YourDentist support team, \n\n")
                    }

                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(Intent.createChooser(intent, "Choose an app"))
                    }
                }

                LogoutButton {
                    vm.logout()
                    activity?.apply {
                        startActivity(
                            Intent(context, MainActivity::class.java).apply {
                                putExtra(Constant.AUTH_SCREEN, true)
                            }
                        )
                        finish()
                    }
                }

            }
        }
    }
}