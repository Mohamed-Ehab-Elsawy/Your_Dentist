package com.nca.yourdentist.domain.local.repository

import android.graphics.Bitmap
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.models.users.Patient

interface HomeRepository {
    fun fetchPatientData(): Patient
    fun fetchDentistData(): Dentist

    fun putQRCodeBitmap(bitmap: Bitmap)
    fun fetchQRCodeBitmap(): Bitmap?
    fun putReminderState(value: Boolean)
    fun fetchReminderState(): Boolean

}