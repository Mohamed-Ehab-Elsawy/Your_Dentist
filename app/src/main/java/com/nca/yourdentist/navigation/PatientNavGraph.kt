package com.nca.yourdentist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nca.yourdentist.navigation.PatientScreens.AboutUsSettings
import com.nca.yourdentist.navigation.PatientScreens.BookAppointment
import com.nca.yourdentist.navigation.PatientScreens.Home
import com.nca.yourdentist.navigation.PatientScreens.Notification
import com.nca.yourdentist.navigation.PatientScreens.NotificationSettings
import com.nca.yourdentist.navigation.PatientScreens.Questionnaire
import com.nca.yourdentist.navigation.PatientScreens.Records
import com.nca.yourdentist.navigation.PatientScreens.Results
import com.nca.yourdentist.navigation.PatientScreens.SelectDentist
import com.nca.yourdentist.navigation.PatientScreens.Settings
import com.nca.yourdentist.navigation.PatientScreens.UploadImage
import com.nca.yourdentist.presentation.screens.patient.appointment.book_appointment_date.BookAppointmentScreen
import com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.SelectDentistScreen
import com.nca.yourdentist.presentation.screens.patient.examination.questionnaire.QuestionnaireScreen
import com.nca.yourdentist.presentation.screens.patient.examination.results.PatientResultsScreen
import com.nca.yourdentist.presentation.screens.patient.examination.upload_image.UploadRadiographScreen
import com.nca.yourdentist.presentation.screens.patient.home.PatientHomeScreen
import com.nca.yourdentist.presentation.screens.patient.notification.PatientNotificationScreen
import com.nca.yourdentist.presentation.screens.patient.records.PatientRecordsScreen
import com.nca.yourdentist.presentation.screens.patient.settings.PatientSettingsScreen
import com.nca.yourdentist.presentation.screens.patient.settings.about_us.PatientAboutUsSettings
import com.nca.yourdentist.presentation.screens.patient.settings.notification_settings.PatientNotificationSettings

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
    data object NotificationSettings : PatientScreens("patient_notification_settings")
    data object AboutUsSettings : PatientScreens("patient_about_us_settings")
}

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
        composable(Records.route) { PatientRecordsScreen(navController) }
        composable(Settings.route) { PatientSettingsScreen(navController) }
        composable(NotificationSettings.route) { PatientNotificationSettings(navController) }
        composable(AboutUsSettings.route) { PatientAboutUsSettings(navController) }
    }
}