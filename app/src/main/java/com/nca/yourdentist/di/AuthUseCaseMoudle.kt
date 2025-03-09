package com.nca.yourdentist.di

import com.nca.yourdentist.domain.repository.AuthRepository
import com.nca.yourdentist.domain.usecase.auth.ForgetPasswordUseCase
import com.nca.yourdentist.domain.usecase.auth.LogoutUseCase
import com.nca.yourdentist.domain.usecase.auth.SignInWithEmailUseCase
import com.nca.yourdentist.domain.usecase.auth.SignInWithGoogleUseCase
import com.nca.yourdentist.domain.usecase.auth.SignupUseCase
import com.nca.yourdentist.domain.usecase.auth.UpdatePatientDataUseCase
import org.koin.dsl.module

val authUseCaseModule = module {
    //Login
    factory { SignInWithEmailUseCase(get<AuthRepository>()) }
    factory { SignInWithGoogleUseCase(get<AuthRepository>()) }
    factory { SignupUseCase(get<AuthRepository>()) }
    //Update
    factory { UpdatePatientDataUseCase(get<AuthRepository>()) }
    //Forget Password
    factory { ForgetPasswordUseCase(get<AuthRepository>()) }
    //Logout
    factory { LogoutUseCase(get<AuthRepository>()) }
}