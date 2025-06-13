package com.nca.yourdentist.presentation.screens.patient.reports.single_report_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.`X-RaysSection`
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.secondaryLight
import com.nca.yourdentist.presentation.screens.patient.reports.PatientReportsViewModel
import com.nca.yourdentist.presentation.screens.patient.reports.single_report_screen.components.RatingSection
import com.nca.yourdentist.utils.Constant
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReportDetailsScreen(
    navController: NavHostController, vm: PatientReportsViewModel = koinViewModel()
) {
    var rating by remember { mutableIntStateOf(0) }
    var rated by remember { mutableStateOf(false) }
    var report by remember { mutableStateOf<Report?>(null) }

    LaunchedEffect(Unit) {
        report = navController.previousBackStackEntry?.savedStateHandle?.get(Constant.REPORT)
        Log.e("ReportDetailsScreen", "ReportDetailsScreen: $report")
    }

    Scaffold(
        topBar = { TopApplicationBar(title = stringResource(R.string.report)) },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_background),
                contentDescription = stringResource(R.string.background),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            if (report != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        if (!rated)
                            RatingSection(
                                rating = rating,
                                dentistName = report!!.dentistName ?: "",
                                onRatingSelected = { rating = it },
                                onSubmitClick = {
                                    rated = true
                                    vm.rateDentist(
                                        report = report!!,
                                        rating = rating
                                    )
                                }
                            )
                    }
                    item {
                        NotesSection(
                            notes = report!!.notes ?: "DR notes",
                            dentistName = report!!.dentistName ?: ""
                        )
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                    item { `X-RaysSection`(report = report!!) }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}

@Composable
fun NotesSection(modifier: Modifier = Modifier, notes: String, dentistName: String) {
    Column(modifier.fillMaxWidth()) {

        Spacer(Modifier.height(32.dp))

        Text(
            text = "DR. Prof. $dentistName, Notes:",
            fontSize = 20.sp,
            color = primaryLight,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = notes,
            fontSize = 18.sp,
            color = secondaryLight
        )

    }
}