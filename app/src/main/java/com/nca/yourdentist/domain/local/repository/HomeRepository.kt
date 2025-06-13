package com.nca.yourdentist.domain.local.repository

import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.models.users.Patient

interface HomeRepository {
    fun fetchPatientData(): Patient
    fun fetchDentistData(): Dentist

    fun putReminderState(value: Boolean)
    fun fetchReminderState(): Boolean

    fun putPatient(patient: Patient)
}