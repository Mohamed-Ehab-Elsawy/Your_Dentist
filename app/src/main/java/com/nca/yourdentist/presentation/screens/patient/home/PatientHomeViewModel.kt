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
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.domain.local.usecase.FetchLocalPatientDataUseCase
import com.nca.yourdentist.domain.local.usecase.FetchReminderStateUseCase
import com.nca.yourdentist.domain.local.usecase.PutReminderStateUseCase
import com.nca.yourdentist.domain.local.usecase.ScheduleToothbrushReminderUseCase
import com.nca.yourdentist.domain.remote.usecase.auth.UploadQRCodeUseCase
import com.nca.yourdentist.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class PatientHomeViewModel(
    private val fetchPatientData: FetchLocalPatientDataUseCase,
    private val uploadQRCodeUseCase: UploadQRCodeUseCase,
    private val putReminderStateUseCase: PutReminderStateUseCase,
    private val fetchReminderStateUseCase: FetchReminderStateUseCase,
    private val scheduleToothbrushReminderUseCase: ScheduleToothbrushReminderUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Bitmap>>(UiState.Idle)
    val uiState: StateFlow<UiState<Bitmap>> = _uiState

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
                _uiState.value = UiState.Success(bitmap)
            } catch (t: Throwable) {
                _uiState.value =
                    UiState.Error(t)
                Log.e("PatientHomeViewModel", "generateQRCodeBitmap: ${t.localizedMessage}")
            }
        }
    }

    private fun fetchReminderState(): Boolean = fetchReminderStateUseCase.invoke()

    private fun putReminderState() = putReminderStateUseCase.invoke(true)

    fun scheduleToothbrushReminder() {
        if (!fetchReminderState()) {
            scheduleToothbrushReminderUseCase.invoke()
            putReminderState()
            Log.e("PatientHomeViewModel", "scheduleToothbrushReminder: Reminder Scheduled")
        }
    }

    fun fetchPatient(): Patient = fetchPatientData()
}