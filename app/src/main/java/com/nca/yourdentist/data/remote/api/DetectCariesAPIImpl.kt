package com.nca.yourdentist.data.remote.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

class DetectCariesAPIImpl(
    private val client: HttpClient
) : DetectCariesAPI {

    override suspend fun detectCaries(file: File): ByteArray {
        return try {
            val result = client.post("http://192.168.0.206:8000/predict/") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("file", file.readBytes(), Headers.build {
                                append(HttpHeaders.ContentDisposition, "filename=\"xray.png\"")
                            })
                        }
                    )
                )
            }

            result.body()
        } catch (t: Throwable) {
            Log.e("DetectCariesAPIImpl", "uploadXray: $t")
            byteArrayOf()
        }
    }
}