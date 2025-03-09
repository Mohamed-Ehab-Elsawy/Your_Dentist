package com.nca.yourdentist.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.nca.yourdentist.data.model.Patient
import com.nca.yourdentist.data.model.requests.AuthRequest

interface AuthRepository {
    suspend fun signup(authRequest: AuthRequest): Result<FirebaseUser?>
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?>
    suspend fun patientLogin(authRequest: AuthRequest): Result<FirebaseUser?>
    suspend fun forgetPassword(email: String)
    suspend fun updateUserData(patient: Patient): Result<Patient>
    suspend fun logout()

    // Dentist functions
    suspend fun dentistLogin(authRequest: AuthRequest): Result<FirebaseUser?>
}