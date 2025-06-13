package com.nca.yourdentist.data.remote.repository

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.response.CariesDetectionResponse
import com.nca.yourdentist.data.remote.api.DetectCariesAPI
import com.nca.yourdentist.data.remote.utils.FirebaseConstants
import com.nca.yourdentist.domain.remote.repository.DetectCariesRepository
import com.nca.yourdentist.utils.convertToImageFile
import kotlinx.coroutines.tasks.await
import java.io.File

class DetectCariesRepositoryImpl(
    private val api: DetectCariesAPI,
    private val preferencesHelper: PreferencesHelper,
    firebaseStorage: FirebaseStorage
) : DetectCariesRepository {
    private val xRaysStorage = firebaseStorage.reference.child(FirebaseConstants.XRAYS)

    override suspend fun uploadXray(imageFile: File): String {
        return try {
            val patient = preferencesHelper.fetchPatient()

            val uploadTask = xRaysStorage
                .child(patient.id ?: "")
                .child(FirebaseConstants.ORIGINAL_XRAYS)
                .child(imageFile.name)
                .putFile(imageFile.toUri())
                .await()

            val downloadUrl = uploadTask.storage.downloadUrl.await()

            Log.e("DetectCariesRepository", "uploadXray: $downloadUrl")

            downloadUrl.toString()
        } catch (t: Throwable) {
            Log.e("DetectCariesRepository", "uploadXray: $t")
            "t"
        }
    }


    override suspend fun uploadDetectedCarriesImage(imageFile: File): String {
        return try {
            val patient = preferencesHelper.fetchPatient()

            val uploadTask = xRaysStorage
                .child(patient.id ?: "")
                .child(FirebaseConstants.DETECTED_CARIES_XRAYS)
                .child(imageFile.name)
                .putFile(imageFile.toUri())
                .await()

            val downloadUrl = uploadTask.storage.downloadUrl.await()

            Log.e("DetectCariesRepository", "uploadDetectedCarriesImage: $downloadUrl")

            downloadUrl.toString()
        } catch (t: Throwable) {
            Log.e("DetectCariesRepository", "uploadDetectedCarriesImage: $t")
            "t"
        }
    }


    override suspend fun detectCaries(imageFile: File, context: Context): CariesDetectionResponse {
        val xrayUrl = uploadXray(imageFile)
        val result = api.detectCaries(imageFile)
        val detectedCarriesUrl =
            uploadDetectedCarriesImage(result.convertToImageFile(context = context))

        return CariesDetectionResponse(
            img = result,
            xrayURL = xrayUrl,
            detectedCariesURL = detectedCarriesUrl
        )
    }

}