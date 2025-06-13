package com.nca.yourdentist.domain.remote.usecase.booking

import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.remote.repository.AppointmentsRepository

class FetchRemoteDentistsUseCase(private val appointmentsRepository: AppointmentsRepository) {
    suspend operator fun invoke(selectedCity: Int, selectedArea: Int) =
        appointmentsRepository.fetchDentists(selectedCity, selectedArea)
}

class FetchAvailableAppointmentsUseCase(private val appointmentsRepository: AppointmentsRepository) {
    suspend operator fun invoke(dentistId: String): Result<List<Appointment>> =
        appointmentsRepository.fetchAvailableAppointments(dentistId)
}

class BookAppointmentUseCase(private val appointmentsRepository: AppointmentsRepository) {
    suspend operator fun invoke(appointment: Appointment): Result<String> =
        appointmentsRepository.bookNewAppointment(appointment)
}

class FetchDentistAppointmentsUseCase(private val appointmentsRepository: AppointmentsRepository) {
    suspend operator fun invoke(dentistId: String): Result<List<Appointment>> =
        appointmentsRepository.fetchDentistAppointments(dentistId)
}

class UpdateAppointmentReportUseCase(private val appointmentsRepository: AppointmentsRepository) {
    suspend operator fun invoke(appointment: Appointment, notes: String) =
        appointmentsRepository.updateAppointmentReport(appointment = appointment, notes = notes)
}