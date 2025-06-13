package com.nca.yourdentist.data.remote.api

import java.io.File

interface DetectCariesAPI {
    suspend fun detectCaries(file: File): ByteArray
}