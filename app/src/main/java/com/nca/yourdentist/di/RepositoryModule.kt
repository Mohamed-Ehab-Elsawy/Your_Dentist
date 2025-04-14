package com.nca.yourdentist.di

import com.nca.yourdentist.data.repository.AuthRepositoryImpl
import com.nca.yourdentist.data.repository.BookingRepositoryImpl
import com.nca.yourdentist.domain.repository.AuthRepository
import com.nca.yourdentist.domain.repository.BookingRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<BookingRepository> { BookingRepositoryImpl(get()) }
}