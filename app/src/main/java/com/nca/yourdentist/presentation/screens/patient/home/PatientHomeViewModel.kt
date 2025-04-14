package com.nca.yourdentist.presentation.screens.patient.home

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.model.Patient
import com.nca.yourdentist.domain.usecase.auth.UploadQRCodeUseCase
import com.nca.yourdentist.utils.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class PatientHomeViewModel(
    private val preferencesHelper: PreferencesHelper,
    private val uploadQRCodeUseCase: UploadQRCodeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Bitmap>>(UiState.Idle)
    val uiState: StateFlow<UiState<Bitmap>> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>()
    val snackBarMessage = _snackBarMessage.asSharedFlow()

    private fun fetchPatientData(): Patient = preferencesHelper.fetchPatient()

    fun fetchQRCodeBitmap() {
        val patient = fetchPatientData()
        _uiState.value = UiState.Loading
        if (patient.qrCode != null) {
            val bitmap = preferencesHelper.fetchQRCodeBitmap()
            if (bitmap != null) {
                _uiState.value = UiState.Success(bitmap)
            } else {
                _uiState.value = UiState.Error(Throwable(message = "Failed to fetch QR Code"))
            }
        } else
            generateQRCodeBitmap()
    }

    private fun uploadQRCode(id: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val data = outputStream.toByteArray()
            uploadQRCodeUseCase.invoke(data, id)
        }
    }

    private fun generateQRCodeBitmap() {
        val patient = fetchPatientData()

        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val id = patient.id!!
                val writer = QRCodeWriter()
                val bitMatrix = writer.encode(id, BarcodeFormat.QR_CODE, 512, 512)
                val bitmap =
                    createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
                for (x in 0 until bitMatrix.width) {
                    for (y in 0 until bitMatrix.height) {
                        bitmap[x, y] =
                            if (bitMatrix[x, y]) "#445E91".toColorInt()
                            else Color.WHITE
                    }
                }
                uploadQRCode(id, bitmap)
                putQRCode(bitmap)
                _uiState.value = UiState.Success(bitmap)
            } catch (t: Throwable) {
                _snackBarMessage.emit(t.localizedMessage ?: "Failed to generate QR code")
                Log.e("PatientHomeViewModel", "generateQRCodeBitmap: ${t.localizedMessage}")
                _uiState.value = UiState.Idle
            }
        }
    }

    fun putQRCode(bitmap: Bitmap) {
        preferencesHelper.putQRCodeBitmap(bitmap)
    }

}