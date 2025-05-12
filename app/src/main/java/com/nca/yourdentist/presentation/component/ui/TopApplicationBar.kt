package com.nca.yourdentist.presentation.component.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.presentation.component.ui.theme.AppTypography
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceContainerLight

@Composable
fun TopApplicationBar(
    title: String,
    icon: ImageVector? = null,
    iconTint: Color? = null,
    onIconClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = surfaceContainerLight
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(surfaceContainerLight)
                .padding(vertical = 18.dp)
        ) {

            Text(
                text = title,
                style = AppTypography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = primaryLight,
                modifier = Modifier.align(Alignment.Center)
            )
            if (icon != null && iconTint != null && onIconClick != null)
                IconButton(
                    onClick = onIconClick,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(32.dp)
                    )
                }
        }
    }
}