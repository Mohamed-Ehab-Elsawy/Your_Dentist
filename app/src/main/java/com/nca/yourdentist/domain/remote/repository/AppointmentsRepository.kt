package com.nca.yourdentist.domain.remote.repository

import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.data.models.users.Dentist

interface AppointmentsRepository {
    suspend fun fetchDentists(selectedCity: Int, selectedArea: Int): Result<List<Dentist>>
    suspend fun fetchAvailableAppointments(dentistId: String): Result<List<Appointment>>
    suspend fun bookNewAppointment(appointment: Appointment): Result<String>

    suspend fun fetchDentistAppointments(dentistId: String): Result<List<Appointment>>
    suspend fun updateAppointmentReport(appointment: Appointment, notes: String)

    suspend fun updatePatientAppointment(appointment: Appointment)
}