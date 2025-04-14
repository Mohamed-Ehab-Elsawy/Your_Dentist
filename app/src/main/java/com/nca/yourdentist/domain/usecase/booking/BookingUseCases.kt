package com.nca.yourdentist.domain.usecase.booking

import com.nca.yourdentist.domain.repository.BookingRepository

class FetchDentistUseCase(private val bookingRepository: BookingRepository) {
    suspend operator fun invoke(selectedCity: Int, selectedArea: Int) =
        bookingRepository.fetchDentists(selectedCity, selectedArea)
}