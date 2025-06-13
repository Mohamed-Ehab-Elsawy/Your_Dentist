package com.nca.yourdentist.presentation.screens.patient.caries_detection.camera.component

import android.Manifest
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    onCapture: (ImageCapture?) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember { mutableStateOf(false) }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasCameraPermission = isGranted
        }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = ContextCompat.getMainExecutor(context)
    LaunchedEffect(Unit) { permissionLauncher.launch(Manifest.permission.CAMERA) }

    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }
        }, modifier = modifier
            .fillMaxWidth()
            .height(500.dp), update = { previewView ->
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                val newImageCapture = ImageCapture.Builder().build()

                val cameraSelector =
                    CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                try {
                    cameraProvider.unbindAll() // Unbind previous use cases
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, newImageCapture
                    )
                    onCapture(newImageCapture)
                } catch (exc: Exception) {
                    Log.e("Camera", "Use case binding failed", exc)
                }
            }, executor)
        })
}