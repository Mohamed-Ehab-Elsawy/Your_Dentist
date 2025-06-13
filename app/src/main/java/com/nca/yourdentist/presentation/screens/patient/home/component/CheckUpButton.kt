package com.nca.yourdentist.presentation.screens.patient.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight

@Composable
fun CheckUpButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick.invoke() }
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
                .align(Alignment.TopStart),
            color = onPrimaryLight
        )
    }
}