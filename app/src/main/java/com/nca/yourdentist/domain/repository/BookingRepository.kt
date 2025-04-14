package com.nca.yourdentist.domain.repository

import com.nca.yourdentist.data.model.Dentist

interface BookingRepository {
    suspend fun fetchDentists(selectedCity: Int, selectedArea: Int): Result<List<Dentist>>
}