package com.nca.yourdentist.data.models.response

data class CariesDetectionResponse(
    val img: ByteArray? = null,
    val xrayURL: String = "",
    val detectedCariesURL: String = "",
    val success: Boolean = true
)