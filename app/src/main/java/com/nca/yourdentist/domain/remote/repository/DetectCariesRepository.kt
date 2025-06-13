package com.nca.yourdentist.domain.remote.repository

import android.content.Context
import com.nca.yourdentist.data.models.response.CariesDetectionResponse
import java.io.File

interface DetectCariesRepository {
    suspend fun uploadXray(imageFile: File): String
    suspend fun uploadDetectedCarriesImage(imageFile: File): String
    suspend fun detectCaries(imageFile: File, context: Context): CariesDetectionResponse
}