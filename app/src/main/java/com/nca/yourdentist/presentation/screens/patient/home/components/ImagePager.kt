package com.nca.yourdentist.presentation.screens.patient.home.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.outlineLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.transparent
import com.nca.yourdentist.presentation.component.ui.theme.white

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImagePager(
    modifier: Modifier = Modifier,
    images: List<Int>,
    hints: List<String>,
    pagerState: PagerState
) {
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