package com.nca.yourdentist.di

import com.nca.yourdentist.domain.repository.BookingRepository
import com.nca.yourdentist.domain.usecase.booking.FetchDentistUseCase
import org.koin.dsl.module

val bookingUseCaseModule = module {
    factory { FetchDentistUseCase(get<BookingRepository>()) }
}