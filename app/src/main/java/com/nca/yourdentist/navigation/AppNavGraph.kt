package com.nca.yourdentist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nca.yourdentist.presentation.screens.dentist.auth.dentist_login.DentistLoginScreen
import com.nca.yourdentist.presentation.screens.common.auth.forget_password.ForgetPasswordScreen
import com.nca.yourdentist.presentation.screens.patient.auth.patient_login.PatientLoginScreen
import com.nca.yourdentist.presentation.screens.patient.auth.patient_signup.PatientSignupScreen
import com.nca.yourdentist.presentation.screens.patient.auth.complete_profile.CompleteProfileScreen
import com.nca.yourdentist.presentation.screens.common.auth.select_type.SelectUserTypeScreen
import com.nca.yourdentist.presentation.screens.common.intro.IntroScreen
import com.nca.yourdentist.presentation.screens.common.splash.SplashScreen
import com.nca.yourdentist.presentation.screens.dentist.home.DentistHomeScreen
import com.nca.yourdentist.presentation.screens.dentist.settings.DentistSettingsScreen
import com.nca.yourdentist.presentation.screens.patient.home.PatientHomeScreen
import com.nca.yourdentist.presentation.screens.patient.settings.PatientSettingsScreen


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

    //Dentist
    data object DentistLogin : Screen("dentist_login_screen")
    data object DentistHome : Screen("dentist_home_screen")
    data object DentistSettings : Screen("dentist_settings_screen")
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        //Common
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Intro.route) { IntroScreen(navController) }
        composable(Screen.SelectUserType.route) { SelectUserTypeScreen(navController) }
        composable(Screen.ForgetPassword.route) { ForgetPasswordScreen(navController) }
        //Patient
        composable(Screen.PatientLogin.route) { PatientLoginScreen(navController) }
        composable(Screen.PatientSignup.route) { PatientSignupScreen(navController) }
        composable(Screen.CompletePatientData.route) { CompleteProfileScreen(navController) }
        composable(Screen.PatientHome.route) { PatientHomeScreen(navController) }
        composable(Screen.PatientSettings.route) { PatientSettingsScreen(navController) }
        //Dentist
        composable(Screen.DentistLogin.route) { DentistLoginScreen(navController) }
        composable(Screen.DentistHome.route) { DentistHomeScreen(navController) }
        composable(Screen.DentistSettings.route) { DentistSettingsScreen(navController) }
    }
}
