package com.nca.yourdentist.presentation.screens.patient.reports.single_report_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.utils.Constant

@Composable
fun SingleReportScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val record =
        navController.previousBackStackEntry?.savedStateHandle?.get<Report>(Constant.HISTORY_RECORD)

    TODO("Implement screen")

}