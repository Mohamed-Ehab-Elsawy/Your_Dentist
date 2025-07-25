package com.nca.yourdentist.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nca.yourdentist.navigation.DentistScreens.AboutUsSettings
import com.nca.yourdentist.navigation.DentistScreens.AppointmentDetailsScreen
import com.nca.yourdentist.navigation.DentistScreens.Home
import com.nca.yourdentist.navigation.DentistScreens.Notifications
import com.nca.yourdentist.navigation.DentistScreens.ScanQRCodeScreen
import com.nca.yourdentist.navigation.DentistScreens.Settings
import com.nca.yourdentist.presentation.screens.dentist.home.DentistHomeScreen
import com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.appointment_details.AppointmentDetailsScreen
import com.nca.yourdentist.presentation.screens.dentist.home.appointment_details.scan_qr_code.ScanQRCodeScreen
import com.nca.yourdentist.presentation.screens.dentist.notification.DentistNotificationScreen
import com.nca.yourdentist.presentation.screens.dentist.notification.DentistNotificationViewModel
import com.nca.yourdentist.presentation.screens.dentist.settings.DentistSettingsScreen
import com.nca.yourdentist.presentation.screens.dentist.settings.about_us.DentistAboutUsSettings
import org.koin.androidx.compose.koinViewModel

sealed class DentistScreens(val route: String) {
    data object Home : DentistScreens("dentist_home_screen")
    data object Notifications : DentistScreens("dentist_notifications_screen")
    data object Settings : DentistScreens("dentist_settings_screen")
    data object ScanQRCodeScreen : DentistScreens("scan_qr_code_screen")
    data object AppointmentDetailsScreen : DentistScreens("dentist_results_screen")

    data object AboutUsSettings : PatientScreens("patient_about_us_settings")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DentistNavGraph(navController: NavHostController) {
    val dentistNotificationVM = koinViewModel<DentistNotificationViewModel>()

    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) { DentistHomeScreen(navController) }
        composable(Notifications.route) { DentistNotificationScreen(dentistNotificationVM) }
        composable(Settings.route) { DentistSettingsScreen(navController) }
        composable(ScanQRCodeScreen.route) { ScanQRCodeScreen(navController) }
        composable(AppointmentDetailsScreen.route) { AppointmentDetailsScreen(navController) }
        composable(AboutUsSettings.route) { DentistAboutUsSettings(navController) }
    }
}