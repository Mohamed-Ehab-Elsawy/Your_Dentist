package com.nca.yourdentist.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nca.yourdentist.navigation.PatientScreens.AboutUsSettings
import com.nca.yourdentist.navigation.PatientScreens.BookAppointment
import com.nca.yourdentist.navigation.PatientScreens.Home
import com.nca.yourdentist.navigation.PatientScreens.Notification
import com.nca.yourdentist.navigation.PatientScreens.Questionnaire
import com.nca.yourdentist.navigation.PatientScreens.Records
import com.nca.yourdentist.navigation.PatientScreens.Results
import com.nca.yourdentist.navigation.PatientScreens.SelectDentist
import com.nca.yourdentist.navigation.PatientScreens.Settings
import com.nca.yourdentist.navigation.PatientScreens.UploadImage
import com.nca.yourdentist.presentation.screens.patient.home.PatientHomeScreen
import com.nca.yourdentist.presentation.screens.patient.home.appointment.book_appointment_date.BookAppointmentScreen
import com.nca.yourdentist.presentation.screens.patient.home.appointment.select_dentist.SelectDentistScreen
import com.nca.yourdentist.presentation.screens.patient.home.examination.questionnaire.QuestionnaireScreen
import com.nca.yourdentist.presentation.screens.patient.home.examination.results.PatientResultsScreen
import com.nca.yourdentist.presentation.screens.patient.home.examination.upload_image.UploadRadiographScreen
import com.nca.yourdentist.presentation.screens.patient.notification.PatientNotificationScreen
import com.nca.yourdentist.presentation.screens.patient.reports.PatientHistoryRecordsScreen
import com.nca.yourdentist.presentation.screens.patient.settings.PatientSettingsScreen
import com.nca.yourdentist.presentation.screens.patient.settings.about_us.PatientAboutUsSettings

sealed class PatientScreens(val route: String) {
    data object Home : PatientScreens("patient_home_screen")
    data object Records : PatientScreens("records")
    data object Notification : PatientScreens("notification")
    data object Settings : PatientScreens("patient_settings")

    data object UploadImage : PatientScreens("patient_upload_image")
    data object Questionnaire : PatientScreens("patient_questionnaire")
    data object Results : PatientScreens("patient_results")
    data object SelectDentist : PatientScreens("select_dentist")
    data object BookAppointment : PatientScreens("book_appointment")

    //Settings
    data object AboutUsSettings : PatientScreens("patient_about_us_settings")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) { PatientHomeScreen(navController) }
        composable(UploadImage.route) { UploadRadiographScreen(navController) }
        composable(Questionnaire.route) { QuestionnaireScreen(navController) }
        composable(Results.route) { PatientResultsScreen(navController) }
        composable(SelectDentist.route) { SelectDentistScreen(navController) }
        composable(BookAppointment.route) { BookAppointmentScreen(navController) }
        composable(Notification.route) { PatientNotificationScreen(navController) }
        composable(Records.route) { PatientHistoryRecordsScreen(navController) }
        composable(Settings.route) { PatientSettingsScreen(navController) }
        composable(AboutUsSettings.route) { PatientAboutUsSettings(navController) }
    }
}