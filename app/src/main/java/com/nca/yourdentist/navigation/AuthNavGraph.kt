package com.nca.yourdentist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nca.yourdentist.navigation.AuthScreens.CompletePatientData
import com.nca.yourdentist.navigation.AuthScreens.DentistLogin
import com.nca.yourdentist.navigation.AuthScreens.ForgetPassword
import com.nca.yourdentist.navigation.AuthScreens.PatientLogin
import com.nca.yourdentist.navigation.AuthScreens.PatientSignup
import com.nca.yourdentist.navigation.AuthScreens.SelectUserType
import com.nca.yourdentist.navigation.AuthScreens.Splash
import com.nca.yourdentist.presentation.screens.common.auth.complete_profile.CompleteProfileScreen
import com.nca.yourdentist.presentation.screens.common.auth.dentist_login.DentistLoginScreen
import com.nca.yourdentist.presentation.screens.common.auth.forget_password.ForgetPasswordScreen
import com.nca.yourdentist.presentation.screens.common.auth.patient_login.PatientLoginScreen
import com.nca.yourdentist.presentation.screens.common.auth.patient_signup.PatientSignupScreen
import com.nca.yourdentist.presentation.screens.common.select_type.SelectUserTypeScreen
import com.nca.yourdentist.presentation.screens.common.splash.SplashScreen

sealed class AuthScreens(val route: String) {
    //Common
    data object Splash : AuthScreens("splash_screen")
    data object SelectUserType : AuthScreens("select_user_type_screen")
    data object ForgetPassword : AuthScreens("forget_password_screen")

    //Patient
    data object PatientLogin : AuthScreens("patient_login_screen")
    data object PatientSignup : AuthScreens("patient_signup_screen")
    data object CompletePatientData : AuthScreens("patient_complete_data_screen")

    //Dentist
    data object DentistLogin : AuthScreens("dentist_login_screen")
}

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Splash.route) {
        //Common
        composable(Splash.route) { SplashScreen(navController) }
        composable(SelectUserType.route) { SelectUserTypeScreen(navController) }
        composable(ForgetPassword.route) { ForgetPasswordScreen(navController) }

        //Patient
        composable(PatientLogin.route) { PatientLoginScreen(navController) }
        composable(PatientSignup.route) { PatientSignupScreen(navController) }
        composable(CompletePatientData.route) { CompleteProfileScreen(navController) }

        //Dentist
        composable(DentistLogin.route) { DentistLoginScreen() }
    }
}