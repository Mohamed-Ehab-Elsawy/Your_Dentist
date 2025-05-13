package com.nca.yourdentist.data.remote.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.requests.AuthRequest
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.data.remote.FirebaseConstants
import com.nca.yourdentist.domain.remote.repository.AuthRepository
import com.nca.yourdentist.presentation.utils.AppProviders
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val preferencesHelper: PreferencesHelper,
    private val firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage
) : AuthRepository {

    private val patientCollection = firestore.collection(FirebaseConstants.PATIENT_COLLECTIONS)
    private val dentistCollection = firestore.collection(FirebaseConstants.DENTIST_COLLECTIONS)

    private val qrCodeStorage =
        firebaseStorage.reference.child(FirebaseConstants.PATIENTS_QR_CODES_STORAGE)

    override suspend fun signup(authRequest: AuthRequest): Result<FirebaseUser?> =
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(
                authRequest.email, authRequest.password
            ).await()

            if (authRequest.patient != null && authRequest.patient?.type == FirebaseConstants.PATIENT) {
                Log.e("AuthRepositoryImpl", "Patient signed up successfully")
                authRequest.patient?.id = result.user?.uid
                patientCollection.document(result.user?.uid!!).set(authRequest.patient!!).await()
                preferencesHelper.putPatient(authRequest.patient!!)
                Result.success(result.user)
            } else
                Result.failure(Throwable("Patient is null"))
        } catch (t: Throwable) {
            Result.failure(t)
        }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?> {
        try {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(firebaseCredential).await()

            val snapshot = patientCollection.document(result.user!!.uid).get().await()
            var currentPatient = snapshot.toObject(Patient::class.java)

            if (currentPatient != null && currentPatient.type == FirebaseConstants.PATIENT)
                preferencesHelper.putPatient(currentPatient)
            else {
                val patient = Patient(
                    name = result.user!!.displayName,
                    email = result.user!!.email, id = result.user!!.uid,
                    phoneNumber = result.user!!.phoneNumber
                )
                patientCollection.document(result.user!!.uid).set(patient).await()
                currentPatient = patient
            }
            AppProviders.patient = currentPatient

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
            val patient = snapshot.toObject(Patient::class.java)
            if (patient != null) {
                preferencesHelper.putPatient(patient)
                AppProviders.patient = patient
                Log.d("AuthRepositoryImpl", "Patient Logged in successfully")
            }
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
            val dentist = snapshot.toObject(Dentist::class.java)
            if (dentist != null) {
                preferencesHelper.putDentist(dentist)
                AppProviders.dentist = dentist
                Log.d("AuthRepositoryImpl", "Dentist Logged in successfully")
            }
            return Result.success(result.user)
        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }

    override suspend fun updateUserData(patient: Patient): Result<Patient> {
        try {
            patientCollection.document(patient.id!!).set(patient).await()
            AppProviders.patient = patient
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

    override suspend fun uploadQRCode(image: ByteArray, patientId: String) {
        val imageRef = qrCodeStorage.child(patientId)
        image.let {
            imageRef.putBytes(it).addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    patientCollection.document(patientId).update("qrCode", downloadUri)
                }
            }
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