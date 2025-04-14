package com.nca.yourdentist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nca.yourdentist.navigation.DentistScreens.Home
import com.nca.yourdentist.navigation.DentistScreens.Notifications
import com.nca.yourdentist.navigation.DentistScreens.Settings
import com.nca.yourdentist.presentation.screens.dentist.home.DentistHomeScreen
import com.nca.yourdentist.presentation.screens.dentist.notification.DentistNotificationScreen
import com.nca.yourdentist.presentation.screens.dentist.settings.DentistSettingsScreen


sealed class DentistScreens(val route: String) {
    data object Home : DentistScreens("dentist_home_screen")
    data object Notifications : DentistScreens("dentist_home_screen")
    data object Settings : DentistScreens("dentist_settings_screen")
}

@Composable
fun DentistNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) { DentistHomeScreen(navController) }
        composable(Notifications.route) { DentistNotificationScreen(navController) }
        composable(Settings.route) { DentistSettingsScreen(navController) }
    }
}