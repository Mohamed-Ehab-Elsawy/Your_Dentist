package com.nca.yourdentist.presentation.component.ui.customized

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.black
import com.nca.yourdentist.presentation.component.ui.theme.white


@Composable
fun ZoomIcon(
    modifier: Modifier = Modifier,
    onZoomIconClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(36.dp)
            .background(black.copy(alpha = 0.6f), shape = CircleShape)
            .clickable { onZoomIconClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_zoom),
            contentDescription = stringResource(R.string.view_x_ray),
            tint = white
        )
    }
}