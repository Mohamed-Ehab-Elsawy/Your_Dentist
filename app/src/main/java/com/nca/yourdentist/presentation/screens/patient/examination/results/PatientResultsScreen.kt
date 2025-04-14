package com.nca.yourdentist.presentation.screens.patient.examination.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.ProgressIndicator
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.customized.CustomButton
import com.nca.yourdentist.presentation.component.ui.theme.errorLight
import com.nca.yourdentist.presentation.component.ui.theme.onErrorLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.screens.patient.examination.questionnaire.QuestionnaireViewModel
import com.nca.yourdentist.utils.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun PatientResultsScreen(
    navController: NavController,
    viewModel: QuestionnaireViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var originalXRayShowDialog by remember { mutableStateOf(false) }
    var resultXRayShowDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopApplicationBar(
                title = stringResource(R.string.results),
                onIconClick = {}
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column {
                Text(
                    stringResource(R.string.uploaded_x_ray),
                    fontSize = 18.sp,
                    color = primaryLight,
                    fontWeight = FontWeight.Bold,
                )
                Box(modifier = Modifier.fillMaxWidth()) {

                    Image(
                        painter = painterResource(R.drawable.img_x_ray),
                        contentDescription = stringResource(R.string.x_ray_image),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.FillBounds
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_zoom),
                        contentDescription = stringResource(R.string.view_x_ray),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(36.dp)
                            .clickable { originalXRayShowDialog = true }
                            .background(
                                Color.Black.copy(alpha = 0.6f),
                            )
                            .padding(8.dp),
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    stringResource(R.string.detected_caries),
                    fontSize = 18.sp,
                    color = primaryLight,
                    fontWeight = FontWeight.Bold,
                )
                Box(modifier = Modifier.fillMaxWidth()) {

                    Image(
                        painter = painterResource(R.drawable.img_result),
                        contentDescription = stringResource(R.string.result_image),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_zoom),
                        contentDescription = stringResource(R.string.view_x_ray),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(36.dp)
                            .clickable { resultXRayShowDialog = true }
                            .background(
                                Color.Black.copy(alpha = 0.6f),
                            )
                            .padding(8.dp),
                        tint = Color.White
                    )
                }

                //SimpleTable()
            }

            Column {
                Text("If there is a Caries you can book an appointment with one of our Dentist")

                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    text = stringResource(R.string.book_now),
                    enabled = uiState !is UiState.Loading,
                    onClick = {
                        navController.navigate(PatientScreens.SelectDentist.route)
                    }
                )
            }
        }

        // Full-Screen Image Dialog
        if (originalXRayShowDialog) {
            Dialog(
                onDismissRequest = { originalXRayShowDialog = false },
            ) {
                ZoomableImage(
                    imageRes = R.drawable.img_x_ray,
                    onClose = { originalXRayShowDialog = false }
                )
            }
        }
        if (resultXRayShowDialog) {
            Dialog(
                onDismissRequest = { resultXRayShowDialog = false },
            ) {
                ZoomableImage(
                    imageRes = R.drawable.img_result,
                    onClose = { resultXRayShowDialog = false }
                )
            }
        }

        when (uiState) {
            is UiState.Loading -> {
                ProgressIndicator()
            }

            is UiState.Success -> {
                LaunchedEffect(Unit) {

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

@Composable
fun ZoomableImage(imageRes: Int, onClose: () -> Unit) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

//    val activity = LocalActivity.current
//    LaunchedEffect(Unit) {
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//    }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 5f)
        offset = offset + panChange
    }

    Dialog(onDismissRequest = { onClose() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { }, // Tap to close
                        onDoubleTap = {
                            scale = if (scale > 1f) 1f else 2f // Toggle zoom
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = stringResource(R.string.x_ray_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .transformable(transformableState),
                contentScale = ContentScale.Fit
            )

            IconButton(
                onClick = { onClose() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
                    .background(Color.Black.copy(alpha = 0.4f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun SimpleTable() {
    Column(modifier = Modifier.padding(16.dp)) {
        // Header Row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Question", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "Answer", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Data Rows
        listOf(
            "Cold drinks pain?" to "Yes",
            "Discomfort biting?" to "No",
            "Brush frequency?" to "Twice a day",
            "Checkup last 6 months?" to "Yes"
        ).forEach { (question, answer) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = question, modifier = Modifier.weight(1f))
                Text(text = answer, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}