package com.nca.yourdentist.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nca.yourdentist.navigation.PatientScreens.AboutUsSettings
import com.nca.yourdentist.navigation.PatientScreens.BookAppointment
import com.nca.yourdentist.navigation.PatientScreens.Camera
import com.nca.yourdentist.navigation.PatientScreens.Home
import com.nca.yourdentist.navigation.PatientScreens.Notification
import com.nca.yourdentist.navigation.PatientScreens.Questionnaire
import com.nca.yourdentist.navigation.PatientScreens.ReportDetails
import com.nca.yourdentist.navigation.PatientScreens.Reports
import com.nca.yourdentist.navigation.PatientScreens.Results
import com.nca.yourdentist.navigation.PatientScreens.SelectDentist
import com.nca.yourdentist.navigation.PatientScreens.Settings
import com.nca.yourdentist.navigation.PatientScreens.UpcomingAppointment
import com.nca.yourdentist.presentation.screens.patient.appointment.book_appointment_date.BookAppointmentScreen
import com.nca.yourdentist.presentation.screens.patient.appointment.select_dentist.SelectDentistScreen
import com.nca.yourdentist.presentation.screens.patient.appointment.upcoming_appointment.UpcomingAppointmentsScreen
import com.nca.yourdentist.presentation.screens.patient.caries_detection.ExaminationViewModel
import com.nca.yourdentist.presentation.screens.patient.caries_detection.camera.CameraScreen
import com.nca.yourdentist.presentation.screens.patient.caries_detection.questionnaire.QuestionnaireScreen
import com.nca.yourdentist.presentation.screens.patient.caries_detection.results.PatientResultsScreen
import com.nca.yourdentist.presentation.screens.patient.home.PatientHomeScreen
import com.nca.yourdentist.presentation.screens.patient.notification.PatientNotificationScreen
import com.nca.yourdentist.presentation.screens.patient.notification.PatientNotificationViewModel
import com.nca.yourdentist.presentation.screens.patient.reports.PatientReportsScreen
import com.nca.yourdentist.presentation.screens.patient.reports.PatientReportsViewModel
import com.nca.yourdentist.presentation.screens.patient.reports.single_report_screen.ReportDetailsScreen
import com.nca.yourdentist.presentation.screens.patient.settings.PatientSettingsScreen
import com.nca.yourdentist.presentation.screens.patient.settings.about_us.PatientAboutUsSettings
import org.koin.androidx.compose.koinViewModel

sealed class PatientScreens(val route: String) {
    // Main 4 screens
    data object Home : PatientScreens("patient_home_screen")
    data object Reports : PatientScreens("patient_records_screen")
    data object Notification : PatientScreens("patient_notification_screen")
    data object Settings : PatientScreens("patient_settings_screen")

    // Examination and booking
    data object Camera : PatientScreens("patient_camera_screen")
    data object Questionnaire : PatientScreens("patient_questionnaire_screen")
    data object Results : PatientScreens("patient_results_screen")
    data object SelectDentist : PatientScreens("patient_select_dentist_screen")
    data object BookAppointment : PatientScreens("patient_book_appointment_screen")

    data object UpcomingAppointment : PatientScreens("patient_upcoming_appointment_screen")
    data object ReportDetails : PatientScreens("patient_report_details_screen")
    data object AboutUsSettings : PatientScreens("patient_about_us_settings_screen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientNavGraph(navController: NavHostController) {
    val notificationVM = koinViewModel<PatientNotificationViewModel>()
    val reportsVM = koinViewModel<PatientReportsViewModel>()
    val examinationVM = koinViewModel<ExaminationViewModel>()
    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) { PatientHomeScreen(navController = navController) }
        composable(Camera.route) { CameraScreen(navController, examinationVM) }
        composable(Questionnaire.route) { QuestionnaireScreen(navController, examinationVM) }
        composable(Results.route) { PatientResultsScreen(navController, examinationVM) }
        composable(SelectDentist.route) { SelectDentistScreen(navController) }
        composable(BookAppointment.route) { BookAppointmentScreen(navController) }
        composable(Notification.route) { PatientNotificationScreen(notificationVM) }
        composable(UpcomingAppointment.route) { UpcomingAppointmentsScreen(navController) }
        composable(Reports.route) { PatientReportsScreen(navController, reportsVM) }
        composable(ReportDetails.route) { ReportDetailsScreen(navController) }
        composable(Settings.route) { PatientSettingsScreen(navController) }
        composable(AboutUsSettings.route) { PatientAboutUsSettings(navController) }
    }
}