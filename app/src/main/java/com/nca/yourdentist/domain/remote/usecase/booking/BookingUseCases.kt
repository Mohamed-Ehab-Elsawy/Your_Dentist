package com.nca.yourdentist.domain.remote.usecase.booking

import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.domain.remote.repository.BookingRepository

class FetchDentistUseCase(private val bookingRepository: BookingRepository) {
    suspend operator fun invoke(selectedCity: Int, selectedArea: Int) =
        bookingRepository.fetchDentists(selectedCity, selectedArea)
}

class FetchAvailableAppointmentsUseCase(private val bookingRepository: BookingRepository) {
    suspend operator fun invoke(dentistId: String): Result<List<Appointment>> =
        bookingRepository.fetchAvailableAppointments(dentistId)
}

class BookAppointmentUseCase(private val bookingRepository: BookingRepository) {
    suspend operator fun invoke(appointment: Appointment): Result<String> =
        bookingRepository.bookAppointment(appointment)
}

class FetchDentistAppointmentsUseCase(private val bookingRepository: BookingRepository) {
    suspend operator fun invoke(dentistId: String): Result<List<Appointment>> =
        bookingRepository.fetchDentistAppointments(dentistId)
}