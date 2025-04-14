package com.nca.yourdentist.presentation.screens.patient.home

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.ProgressIndicator
import com.nca.yourdentist.presentation.component.ui.TopApplicationBar
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.screens.patient.home.components.ImagePager
import com.nca.yourdentist.presentation.screens.patient.home.components.QRCodeDialog
import com.nca.yourdentist.presentation.screens.patient.home.components.WelcomeSection
import com.nca.yourdentist.utils.AppProviders
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PatientHomeScreen(
    navController: NavController, viewModel: PatientHomeViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()

    // State to control the dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    val images = listOf(
        R.drawable.img_xray,
        R.drawable.img_questionnaire,
        R.drawable.img_examination
    )

    val hints = listOf(
        stringResource(R.string.easily_analyze_your_x_ray_and_get_a_detailed_report),
        stringResource(R.string.answer_a_quick_questionnaire_to_help_us_better_understand_your_condition),
        stringResource(R.string.book_a_specialist_dentist_to_start_treatment_the_fastest_way)
    )

    LaunchedEffect(Unit) { viewModel.fetchQRCodeBitmap() }
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            scope.launch {
                val nextPage = (pagerState.currentPage + 1) % images.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Scaffold(
        topBar = {
            TopApplicationBar(title = stringResource(R.string.home))
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.padding(top = 16.dp))

                WelcomeSection { showDialog = true }

                Spacer(Modifier.height(16.dp))

                ImagePager(
                    images = images,
                    hints = hints,
                    pagerState = pagerState
                )

                Spacer(Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            navController.navigate(PatientScreens.UploadImage.route)
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_home),
                        contentDescription = "home img",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .matchParentSize()
                    )
                    Text(
                        text = stringResource(R.string.check_up_your_teeth_now),
                        style = AppTypography.titleLarge,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .align(Alignment.TopStart), color = onPrimaryLight
                    )
                }

                // Dialog to preview the QR code
                if (showDialog) {
                    QRCodeDialog(
                        onDismiss = { showDialog = false },
                        putQrCode = {
                            Log.e("YourDentist", "putQrCode: $it")
                            viewModel.putQRCode(it)
                        })
                }
            }
        }
    }

    when (uiState) {
        is UiState.Loading -> ProgressIndicator()

        is UiState.Success -> {
            val patientQrCodeBitmap = (uiState as UiState.Success<Bitmap>).data
            AppProviders.patientQRCodeBitmap = patientQrCodeBitmap
        }

        is UiState.Error -> {}

        is UiState.Idle -> {}
    }

}