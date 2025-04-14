package com.nca.yourdentist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nca.yourdentist.navigation.MainScreens.CompletePatientData
import com.nca.yourdentist.navigation.MainScreens.DentistLogin
import com.nca.yourdentist.navigation.MainScreens.ForgetPassword
import com.nca.yourdentist.navigation.MainScreens.Intro
import com.nca.yourdentist.navigation.MainScreens.PatientLogin
import com.nca.yourdentist.navigation.MainScreens.PatientSignup
import com.nca.yourdentist.navigation.MainScreens.SelectUserType
import com.nca.yourdentist.navigation.MainScreens.Splash
import com.nca.yourdentist.presentation.screens.common.auth.forget_password.ForgetPasswordScreen
import com.nca.yourdentist.presentation.screens.common.auth.select_type.SelectUserTypeScreen
import com.nca.yourdentist.presentation.screens.common.intro.IntroScreen
import com.nca.yourdentist.presentation.screens.common.splash.SplashScreen
import com.nca.yourdentist.presentation.screens.dentist.auth.dentist_login.DentistLoginScreen
import com.nca.yourdentist.presentation.screens.patient.auth.complete_profile.CompleteProfileScreen
import com.nca.yourdentist.presentation.screens.patient.auth.patient_login.PatientLoginScreen
import com.nca.yourdentist.presentation.screens.patient.auth.patient_signup.PatientSignupScreen

sealed class MainScreens(val route: String) {
    //Common
    data object Splash : MainScreens("splash_screen")
    data object Intro : MainScreens("intro_screen")
    data object SelectUserType : MainScreens("select_user_type_screen")
    data object ForgetPassword : MainScreens("forget_password_screen")

    //Patient
    data object PatientLogin : MainScreens("patient_login_screen")
    data object PatientSignup : MainScreens("patient_signup_screen")
    data object CompletePatientData : MainScreens("patient_complete_data_screen")

    //Dentist
    data object DentistLogin : MainScreens("dentist_login_screen")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Splash.route) {
        //Common
        composable(Splash.route) { SplashScreen(navController) }
        composable(Intro.route) { IntroScreen(navController) }
        composable(SelectUserType.route) { SelectUserTypeScreen(navController) }
        composable(ForgetPassword.route) { ForgetPasswordScreen(navController) }

        //Patient
        composable(PatientLogin.route) { PatientLoginScreen(navController) }
        composable(PatientSignup.route) { PatientSignupScreen(navController) }
        composable(CompletePatientData.route) { CompleteProfileScreen(navController) }

        //Dentist
        composable(DentistLogin.route) { DentistLoginScreen(navController) }
    }
}