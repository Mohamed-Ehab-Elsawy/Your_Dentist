package com.nca.yourdentist.presentation.component.ui.customized

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nca.yourdentist.R
import com.nca.yourdentist.data.models.Report
import java.io.File


@Composable
fun `X-RaysSection`(
    modifier: Modifier = Modifier,
    report: Report,
    uploadedImageFile: File? = null,
    detectedCariesImageFile: File? = null
) {
    var originalXRayShowDialog by remember { mutableStateOf(false) }
    var resultXRayShowDialog by remember { mutableStateOf(false) }

    if (originalXRayShowDialog || resultXRayShowDialog) {
        Dialog(
            onDismissRequest = {
                originalXRayShowDialog = false
                resultXRayShowDialog = false
            },
        ) {
            ZoomableImage(
                imgURL =
                    if (originalXRayShowDialog) uploadedImageFile
                    else detectedCariesImageFile,
                onClose = {
                    originalXRayShowDialog = false
                    resultXRayShowDialog = false
                }
            )
        }
    }

    Column(modifier = modifier) {
        // User input image
        `X-RayImage`(
            title = stringResource(R.string.uploaded_x_ray),
            imageURL = report.uploadedImageUrl,
            imageFile = uploadedImageFile,
            onZoomIconClick = { originalXRayShowDialog = true }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Output image
        `X-RayImage`(
            title = stringResource(R.string.detected_caries),
            imageURL = report.detectedCariesImageUrl,
            imageFile = detectedCariesImageFile,
            onZoomIconClick = { resultXRayShowDialog = true }
        )
    }
}