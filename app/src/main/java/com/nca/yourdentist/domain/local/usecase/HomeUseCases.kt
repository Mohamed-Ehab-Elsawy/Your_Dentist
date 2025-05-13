package com.nca.yourdentist.domain.local.usecase

import android.graphics.Bitmap
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.domain.local.repository.HomeRepository

class PutQRCodeBitmapUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(bitmap: Bitmap) = homeRepository.putQRCodeBitmap(bitmap)
}

class FetchQRCodeBitmapUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): Bitmap? = homeRepository.fetchQRCodeBitmap()
}

class FetchLocalPatientDataUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): Patient = homeRepository.fetchPatientData()
}

class FetchLocalDentistDataUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): Dentist = homeRepository.fetchDentistData()
}

class FetchReminderStateUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): Boolean = homeRepository.fetchReminderState()
}

class PutReminderStateUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(value: Boolean) = homeRepository.putReminderState(value)
}