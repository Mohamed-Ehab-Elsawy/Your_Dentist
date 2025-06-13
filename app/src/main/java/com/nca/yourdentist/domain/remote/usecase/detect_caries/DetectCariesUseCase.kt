package com.nca.yourdentist.domain.remote.usecase.detect_caries

import android.content.Context
import com.nca.yourdentist.data.models.response.CariesDetectionResponse
import com.nca.yourdentist.domain.remote.repository.DetectCariesRepository
import java.io.File

class DetectCariesUseCase(private val detectCariesRepository: DetectCariesRepository) {
    suspend operator fun invoke(imageFile: File, context: Context): CariesDetectionResponse {
        return detectCariesRepository.detectCaries(imageFile, context)
    }
}