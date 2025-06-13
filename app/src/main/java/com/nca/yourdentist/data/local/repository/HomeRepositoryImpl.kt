package com.nca.yourdentist.data.local.repository

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

    override fun putReminderState(value: Boolean) =
        preferencesHelper.putBoolean(REMINDER_STATE, value)

    override fun fetchReminderState(): Boolean = preferencesHelper.fetchBoolean(REMINDER_STATE)

    override fun putPatient(patient: Patient) = preferencesHelper.putPatient(patient)

}