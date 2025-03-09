package com.nca.yourdentist.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.model.Dentist
import com.nca.yourdentist.data.model.Patient
import com.nca.yourdentist.data.model.requests.AuthRequest
import com.nca.yourdentist.domain.repository.AuthRepository
import com.nca.yourdentist.utils.Constant
import com.nca.yourdentist.utils.providers.DentistProvider
import com.nca.yourdentist.utils.providers.PatientProvider
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore
) : AuthRepository {



    private val patientCollection = firestore.collection(Constant.PATIENT_COLLECTIONS)
    private val dentistCollection = firestore.collection(Constant.DENTIST_COLLECTIONS)

    override suspend fun signup(authRequest: AuthRequest): Result<FirebaseUser?> =
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(
                authRequest.email, authRequest.password
            ).await()
            authRequest.patient?.id = result.user?.uid
            patientCollection.document(result.user?.uid!!).set(authRequest.patient!!).await()
            PatientProvider.patient = authRequest.patient
            Result.success(result.user)
        } catch (t: Throwable) {
            Result.failure(t)
        }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?> {
        try {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(firebaseCredential).await()

            val snapshot = patientCollection.document(result.user!!.uid).get().await()
            var currentPatient = snapshot.toObject(Patient::class.java)

            if (currentPatient == null) {
                val patient = Patient(
                    name = result.user!!.displayName,
                    email = result.user!!.email,
                    id = result.user!!.uid,
                    phoneNumber = result.user!!.phoneNumber
                )
                patientCollection.document(result.user!!.uid).set(patient).await()
                currentPatient = patient
            }
            PatientProvider.patient = currentPatient
            return Result.success(result.user)
        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }

    override suspend fun patientLogin(authRequest: AuthRequest): Result<FirebaseUser?> {
        try {
            val result =
                firebaseAuth.signInWithEmailAndPassword(
                    authRequest.email,
                    authRequest.password
                ).await()
            val snapshot =
                patientCollection.document(result.user!!.uid).get().await()
            PatientProvider.patient = snapshot.toObject(Patient::class.java)
            return Result.success(result.user)
        } catch (t: Throwable) {
            return Result.failure(t)
        }

    }

    override suspend fun dentistLogin(authRequest: AuthRequest): Result<FirebaseUser?> {
        try {
            val result =
                firebaseAuth.signInWithEmailAndPassword(
                    authRequest.email,
                    authRequest.password
                ).await()

            val snapshot =
                dentistCollection.document(result.user!!.uid).get().await()
            DentistProvider.dentist = snapshot.toObject(Dentist::class.java)
            return Result.success(result.user)
        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }

    override suspend fun updateUserData(patient: Patient): Result<Patient> {
        try {
            patientCollection.document(patient.id!!).set(patient).await()
            PatientProvider.patient = patient
            return Result.success(patient)
        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }

    override suspend fun forgetPassword(email: String) {
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
        } catch (t: Throwable) {
            Log.e("FirebaseServicesImpl", "forgetPassword: ${t.localizedMessage}")
        }
    }

    override suspend fun logout() {
        try {
            firebaseAuth.signOut()
        } catch (t: Throwable) {
            Log.e("FirebaseServicesImpl", "logout: ${t.localizedMessage}")
        }
    }

}