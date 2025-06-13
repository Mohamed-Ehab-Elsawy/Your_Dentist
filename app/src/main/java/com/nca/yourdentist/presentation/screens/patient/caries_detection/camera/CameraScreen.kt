package com.nca.yourdentist.presentation.screens.patient.caries_detection.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.nca.yourdentist.navigation.PatientScreens
import com.nca.yourdentist.presentation.component.ui.theme.black
import com.nca.yourdentist.presentation.screens.patient.caries_detection.ExaminationViewModel
import com.nca.yourdentist.presentation.screens.patient.caries_detection.camera.component.CameraView
import com.nca.yourdentist.presentation.screens.patient.caries_detection.camera.component.GalleryButton
import com.nca.yourdentist.presentation.screens.patient.caries_detection.camera.component.ShutterButton
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.getFileFromUri
import java.io.File

@Composable
fun CameraScreen(navController: NavController, vm: ExaminationViewModel) {
    val context = LocalContext.current

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    val photoFile = uri.getFileFromUri(context)
                    vm.detectCaries(photoFile, context)
                    navController.apply {
                        navigate(PatientScreens.Questionnaire.route)
                    }
                }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {

        CameraView(modifier = Modifier.align(Alignment.Center), onCapture = { imageCapture = it })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            ShutterButton(
                modifier = Modifier.align(Alignment.Center), onClick = {
                    onImageTaken(
                        capture = imageCapture, context = context, onSuccess = { photoFile ->
                            vm.detectCaries(photoFile, context)
                            navController.apply {
                                currentBackStackEntry?.savedStateHandle?.set(
                                    Constant.UPLOADED_FILE, photoFile
                                )
                                navigate(PatientScreens.Questionnaire.route)
                            }
                        })
                })
            GalleryButton(modifier = Modifier.align(Alignment.CenterStart)) {
                val intent =
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                imagePickerLauncher.launch(intent)
            }
        }
    }
}

fun onImageTaken(capture: ImageCapture?, context: Context, onSuccess: (File) -> Unit) {
    val executor = ContextCompat.getMainExecutor(context)
    capture?.let { capture ->
        val photoFile = File(context.externalCacheDir, "${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        capture.takePicture(
            outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onSuccess.invoke(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            })
    }
}