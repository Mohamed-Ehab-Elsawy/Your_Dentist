package com.nca.yourdentist.presentation.screens.patient.reports

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.customized.NoContentSection
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.screens.patient.reports.components.ReportItem
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant

@Composable
fun PatientReportsScreen(
    navController: NavController,
    viewModel: PatientReportsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    var reports by remember { mutableStateOf<List<Report>>(emptyList()) }

    Scaffold(
        topBar = {
            TopApplicationBar(title = stringResource(R.string.your_reports))
        }) { paddingValues ->

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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (reports.isNotEmpty())
                    items(reports.size) { index ->
                        ReportItem(
                            report = reports[index],
                            onClick = {
                                navController.apply {
                                    currentBackStackEntry?.savedStateHandle?.set(
                                        Constant.REPORT,
                                        it
                                    )
                                    navController.navigate(PatientScreens.ReportDetails.route)
                                }
                            }
                        )
                    }
                else
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        )
                        {
                            NoContentSection(
                                modifier = Modifier.wrapContentSize(),
                                painter = R.drawable.img_no_records,
                                title = stringResource(R.string.no_records_yet)
                            )
                        }
                    }
            }

        }
    }

    when (uiState) {

        is UiState.Success -> {
            LaunchedEffect(uiState) {
                val newList = (uiState as UiState.Success<List<Report>>).data
                if (newList.isNotEmpty()) reports = newList
            }
        }

        is UiState.Error -> {
            LaunchedEffect(uiState) {
                snackBarHostState.showSnackbar(
                    (uiState as UiState.Error).t.localizedMessage ?: "Error occurred"
                )
            }
        }

        else -> {}
    }
}