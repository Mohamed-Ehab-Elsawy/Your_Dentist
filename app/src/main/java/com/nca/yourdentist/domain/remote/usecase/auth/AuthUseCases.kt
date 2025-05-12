package com.nca.yourdentist.domain.remote.usecase.auth

import com.google.firebase.auth.FirebaseUser
import com.nca.yourdentist.data.models.requests.AuthRequest
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.domain.remote.repository.AuthRepository

class SignupUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(authRequest: AuthRequest): Result<FirebaseUser?> {
        return authRepository.signup(authRequest)
    }
}

class SignInWithEmailUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        authRequest: AuthRequest, isDentist: Boolean
    ): Result<FirebaseUser?> {
        return if (isDentist) {
            authRepository.dentistLogin(authRequest)
        } else {
            authRepository.patientLogin(authRequest)
        }
    }
}

class SignInWithGoogleUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(idToken: String): Result<FirebaseUser?> {
        return authRepository.signInWithGoogle(idToken)
    }
}

class UpdatePatientDataUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(patient: Patient): Result<Patient> {
        return authRepository.updateUserData(patient)
    }
}

class UploadQRCodeUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(image: ByteArray, patientId: String) {
        authRepository.uploadQRCode(image, patientId)
    }
}

class ForgetPasswordUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String) {
        authRepository.forgetPassword(email)
    }
}

class RemoteLogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}