package com.nca.yourdentist.presentation.screens.patient.caries_detection.results

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Questionnaire
import com.nca.yourdentist.data.models.Report
import com.nca.yourdentist.data.models.response.CariesDetectionResponse
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.customized.ProgressDialog
import com.nca.yourdentist.presentation.component.ui.customized.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.`X-RaysSection`
import com.nca.yourdentist.presentation.component.ui.customized.ZoomableImage
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorLight
import com.nca.yourdentist.presentation.screens.patient.caries_detection.ExaminationViewModel
import com.nca.yourdentist.presentation.screens.patient.caries_detection.results.component.BookNowSection
import com.nca.yourdentist.presentation.utils.UiState
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.convertToImageFile
import java.io.File

@Composable
fun PatientResultsScreen(
    navController: NavController,
    viewModel: ExaminationViewModel
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    var originalXRayShowDialog by remember { mutableStateOf(false) }
    var resultXRayShowDialog by remember { mutableStateOf(false) }

    var uploadedXRay by remember { mutableStateOf<File?>(null) }
    var resultsXRay by remember { mutableStateOf<File?>(null) }
    var questionnaire = remember { mutableStateListOf<Questionnaire>() }
    var report by remember { mutableStateOf<Report>(Report()) }

    LaunchedEffect(Unit) {
        questionnaire.addAll(viewModel.questionnaire)
        uploadedXRay = viewModel.uploadedXRay
        report.questionnaire = questionnaire
//        Log.e("PatientResultsScreen", "questionnaire: ${report.questionnaire}")
//        Log.e("PatientResultsScreen", "uploadedXRay path: ${report.uploadedImageUrl}")
    }

    Scaffold(
        topBar = {
            TopApplicationBar(
                title = stringResource(R.string.results)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = errorLight,
                    contentColor = onErrorLight
                )
            }
        }
    ) { paddingValues ->
        Box(
            Modifier
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    `X-RaysSection`(
                        report = report,
                        uploadedImageFile = uploadedXRay,
                        detectedCariesImageFile = resultsXRay
                    )
                }
                item { Spacer(Modifier.height(16.dp)) }
                item {
                    BookNowSection(
                        enabled = uiState !is UiState.Loading,
                        onBookNowClick = {
                            navController.apply {
                                currentBackStackEntry?.savedStateHandle[Constant.REPORT] = report
                                navigate(PatientScreens.SelectDentist.route)
                            }
                        }
                    )
                }
            }
        }


        if (originalXRayShowDialog || resultXRayShowDialog) {
            Dialog(
                onDismissRequest = {
                    originalXRayShowDialog = false
                    resultXRayShowDialog = false
                },
            ) {
                ZoomableImage(
                    imgURL =
                        if (originalXRayShowDialog) uploadedXRay
                        else resultsXRay,
                    onClose = {
                        originalXRayShowDialog = false
                        resultXRayShowDialog = false
                    }
                )
            }
        }

        when (uiState) {
            is UiState.Loading -> ProgressDialog()


            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    val response = (uiState as UiState.Success<*>).data as CariesDetectionResponse
                    Log.e("PatientResultsScreen", "response: ${response.copy(img = null)}")
                    resultsXRay = response.img!!.convertToImageFile(context = context)
                    report.uploadedImageUrl = response.xrayURL
                    report.detectedCariesImageUrl = response.detectedCariesURL
                }
            }

            is UiState.Error -> {
                LaunchedEffect(Unit) {

                }
            }

            is UiState.Idle -> {}
        }
    }
}