package com.nca.yourdentist.presentation.screens.patient.caries_detection.camera.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nca.yourdentist.R
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight


@Composable
fun ShutterButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 16.dp)
            .size(64.dp)
            .background(primaryLight, shape = CircleShape)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_camera_ai),
            contentDescription = stringResource(R.string.take_a_pic),
            modifier = Modifier.size(32.dp),
            tint = onPrimaryLight
        )
    }
}