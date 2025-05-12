package com.nca.yourdentist.presentation.screens.patient.home.examination.upload_image

import android.Manifest
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.nca.yourdentist.R
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.theme.onPrimaryLight
import com.nca.yourdentist.presentation.component.ui.theme.primaryLight
import com.nca.yourdentist.presentation.screens.patient.home.examination.ExaminationViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun UploadRadiographScreen(
    navController: NavController,
    vm: ExaminationViewModel = koinViewModel()
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

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val photoFile = getFileFromUri(context, uri)
            vm.uploadXray(photoFile)
            navController.navigate(PatientScreens.Questionnaire.route)
        }
    }

    LaunchedEffect(Unit) { permissionLauncher.launch(Manifest.permission.CAMERA) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp) // Adjusted height
                .align(Alignment.Center),
            update = { previewView ->
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }

                    val newImageCapture = ImageCapture.Builder().build()

                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    try {
                        cameraProvider.unbindAll() // Unbind previous use cases
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            newImageCapture
                        )
                        imageCapture = newImageCapture // Assign the new instance
                    } catch (exc: Exception) {
                        Log.e("Camera", "Use case binding failed", exc)
                    }
                }, executor)
            }
        )

        // Controls: Gallery & Shutter Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            // Shutter Button (Centered)
            IconButton(
                onClick = {
                    imageCapture?.let { capture ->
                        val photoFile =
                            File(context.externalCacheDir, "${System.currentTimeMillis()}.jpg")
                        val outputOptions =
                            ImageCapture.OutputFileOptions.Builder(photoFile).build()
                        capture.takePicture(
                            outputOptions,
                            executor,
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    vm.uploadXray(photoFile)
                                    navController.navigate(PatientScreens.Questionnaire.route)
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    exception.printStackTrace()
                                }
                            }
                        )
                    }
                },
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(64.dp)
                    .align(Alignment.Center)
                    .background(primaryLight, shape = CircleShape)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_camera_ai),
                    contentDescription = stringResource(R.string.take_a_pic),
                    modifier = Modifier.size(32.dp),
                    tint = onPrimaryLight
                )
            }

            // Gallery Button
            IconButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(64.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_photo_album),
                    contentDescription = stringResource(R.string.open_gallery),
                    modifier = Modifier.size(32.dp),
                    tint = primaryLight
                )
            }
        }
    }
}

fun getFileFromUri(context: Context, uri: Uri): File {
    val contentResolver = context.contentResolver

    if (uri.scheme == "file") {
        return File(uri.path!!)
    }

    val fileName = getFileName(context, uri)
    val tempFile = File(context.cacheDir, fileName)

    contentResolver.openInputStream(uri)?.use { inputStream ->
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    return tempFile
}

fun getFileName(context: Context, uri: Uri): String {
    var name: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    return name ?: "${System.currentTimeMillis()}.jpg"
}