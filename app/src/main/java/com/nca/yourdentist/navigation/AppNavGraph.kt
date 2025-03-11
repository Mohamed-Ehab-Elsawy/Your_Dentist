package com.nca.yourdentist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nca.yourdentist.presentation.screens.common.auth.forget_password.ForgetPasswordScreen
import com.nca.yourdentist.presentation.screens.common.auth.select_type.SelectUserTypeScreen
import com.nca.yourdentist.presentation.screens.common.intro.IntroScreen
import com.nca.yourdentist.presentation.screens.common.splash.SplashScreen
import com.nca.yourdentist.presentation.screens.dentist.auth.dentist_login.DentistLoginScreen
import com.nca.yourdentist.presentation.screens.dentist.home.DentistHomeScreen
import com.nca.yourdentist.presentation.screens.dentist.settings.DentistSettingsScreen
import com.nca.yourdentist.presentation.screens.patient.auth.complete_profile.CompleteProfileScreen
import com.nca.yourdentist.presentation.screens.patient.auth.patient_login.PatientLoginScreen
import com.nca.yourdentist.presentation.screens.patient.auth.patient_signup.PatientSignupScreen
import com.nca.yourdentist.presentation.screens.patient.home.PatientHomeScreen
import com.nca.yourdentist.presentation.screens.patient.settings.PatientSettingsScreen
import com.nca.yourdentist.presentation.screens.patient.settings.about_us.PatientAboutUsSettings
import com.nca.yourdentist.presentation.screens.patient.settings.notification_settings.PatientNotificationSettings


sealed class Screen(val route: String) {
    //Common
    data object Splash : Screen("splash_screen")
    data object Intro : Screen("intro_screen")
    data object SelectUserType : Screen("select_user_type_screen")
    data object ForgetPassword : Screen("forget_password_screen")

    //Patient
    data object PatientLogin : Screen("patient_login_screen")
    data object PatientSignup : Screen("patient_signup_screen")
    data object CompletePatientData : Screen("patient_complete_data_screen")
    data object PatientHome : Screen("patient_home_screen")
    data object PatientSettings : Screen("patient_settings")
    data object PatientNotificationSettings : Screen("patient_notification_settings")
    data object PatientAboutUsSettings : Screen("patient_about_us_settings")

    //Dentist
    data object DentistLogin : Screen("dentist_login_screen")
    data object DentistHome : Screen("dentist_home_screen")
    data object DentistSettings : Screen("dentist_settings_screen")
}

@Composable
fun AppNavGraph() {
    val navigator = rememberNavController()

    NavHost(navController = navigator, startDestination = Screen.Splash.route) {
        //Common
        composable(Screen.Splash.route) { SplashScreen(navigator) }
        composable(Screen.Intro.route) { IntroScreen(navigator) }
        composable(Screen.SelectUserType.route) { SelectUserTypeScreen(navigator) }
        composable(Screen.ForgetPassword.route) { ForgetPasswordScreen(navigator) }
        //Patient
        composable(Screen.PatientLogin.route) { PatientLoginScreen(navigator) }
        composable(Screen.PatientSignup.route) { PatientSignupScreen(navigator) }
        composable(Screen.CompletePatientData.route) { CompleteProfileScreen(navigator) }
        composable(Screen.PatientHome.route) { PatientHomeScreen(navigator) }
        composable(Screen.PatientSettings.route) { PatientSettingsScreen(navigator) }
        composable(Screen.PatientNotificationSettings.route) { PatientNotificationSettings(navigator) }
        composable(Screen.PatientAboutUsSettings.route) { PatientAboutUsSettings(navigator) }
        //Dentist
        composable(Screen.DentistLogin.route) { DentistLoginScreen(navigator) }
        composable(Screen.DentistHome.route) { DentistHomeScreen(navigator) }
        composable(Screen.DentistSettings.route) { DentistSettingsScreen(navigator) }
    }
}
