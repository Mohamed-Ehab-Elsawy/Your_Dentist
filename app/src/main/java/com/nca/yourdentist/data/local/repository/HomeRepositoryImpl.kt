package com.nca.yourdentist.data.local.repository

import android.graphics.Bitmap
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.local.PreferencesHelper.Companion.REMINDER_STATE
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.domain.local.repository.HomeRepository

class HomeRepositoryImpl(
    private val preferencesHelper: PreferencesHelper
) : HomeRepository {

    override fun fetchPatientData(): Patient = preferencesHelper.fetchPatient()

    override fun fetchDentistData(): Dentist = preferencesHelper.fetchDentist()

    override fun putQRCodeBitmap(bitmap: Bitmap) = preferencesHelper.putQRCodeBitmap(bitmap)

    override fun fetchQRCodeBitmap(): Bitmap? = preferencesHelper.fetchQRCodeBitmap()

    override fun putReminderState(value: Boolean) =
        preferencesHelper.putBoolean(REMINDER_STATE, value)

    override fun fetchReminderState(): Boolean = preferencesHelper.fetchBoolean(REMINDER_STATE)

}