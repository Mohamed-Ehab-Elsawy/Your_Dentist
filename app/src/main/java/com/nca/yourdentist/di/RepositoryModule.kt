package com.nca.yourdentist.di

import com.nca.yourdentist.data.repository.AuthRepositoryImpl
import com.nca.yourdentist.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}