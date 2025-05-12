package com.nca.yourdentist.presentation.screens.patient.home.home_components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.outlineLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.transparent
import com.nca.yourdentist.presentation.component.ui.theme.white
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerSection(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
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

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % images.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Card(
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = white),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                count = images.size,
                state = pagerState,
                modifier = Modifier.matchParentSize()
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = images[page]),
                        contentDescription = "Slide Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Text(
                        text = hints[page],
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopStart)
                            .background(color = transparent),
                        style = AppTypography.titleSmall,
                        color = primaryLight
                    )
                }
            }

            PagerIndicator(
                modifier = Modifier.align(Alignment.BottomCenter),
                pagerState = pagerState,
                pageCount = images.size,
                activeColor = primaryLight,
                inactiveColor = outlineLight
            )
        }
    }

}