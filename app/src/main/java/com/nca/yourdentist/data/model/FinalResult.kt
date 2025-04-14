package com.nca.yourdentist.data.model

import java.io.File

data class FinalResult(
    val id: Int? = null,
    val originalXRay: File? = null,
    val detectedCariesXRay: File? = null,
    val questionnaireResult: List<ItemQuestion>? = null
)