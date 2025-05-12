package com.nca.yourdentist.domain.remote.repository

import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.data.models.users.Dentist

interface BookingRepository {
    suspend fun fetchDentists(selectedCity: Int, selectedArea: Int): Result<List<Dentist>>
    suspend fun fetchAvailableAppointments(dentistId: String): Result<List<Appointment>>
    suspend fun bookAppointment(appointment: Appointment): Result<String>

    suspend fun fetchDentistAppointments(dentistId: String): Result<List<Appointment>>
}