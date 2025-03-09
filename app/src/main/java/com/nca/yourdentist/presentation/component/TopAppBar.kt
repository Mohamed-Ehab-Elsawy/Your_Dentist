package com.nca.yourdentist.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.component.ui.theme.surfaceContainerLight

@Composable
fun CustomAppBar(
    title: String,
    iconRes: Int,
    iconTint: Color,
    onIconClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = surfaceContainerLight // Ensure background color consistency
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth() // Ensure Box expands to full width
                .background(surfaceContainerLight)
                .padding(vertical = 16.dp)
        ) {
            // Title (Centered)
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 32.sp,
                color = primaryLight,
                modifier = Modifier.align(Alignment.Center)
            )

            // Generic Icon Button (End)
            IconButton(
                onClick = onIconClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd) // Aligns to end
                    .padding(end = 16.dp) // Ensure spacing from the edge
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}
