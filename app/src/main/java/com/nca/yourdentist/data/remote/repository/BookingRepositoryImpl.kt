package com.nca.yourdentist.data.remote.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.remote.FirebaseConstants
import com.nca.yourdentist.domain.models.AppointmentBookResponse
import com.nca.yourdentist.domain.models.AppointmentStatus
import com.nca.yourdentist.domain.remote.repository.BookingRepository
import kotlinx.coroutines.tasks.await

class BookingRepositoryImpl(
    firestore: FirebaseFirestore
) : BookingRepository {
    private val dentistCollection = firestore.collection(FirebaseConstants.DENTIST_COLLECTIONS)

    override suspend fun fetchDentists(
        selectedCity: Int,
        selectedArea: Int
    ): Result<List<Dentist>> = try {
        Log.e(
            "FireStoreQuery",
            "Fetching dentists for city: $selectedCity, area: $selectedArea"
        )

        val snapshot = dentistCollection
            .whereEqualTo("clinic.city", selectedCity)
            .whereEqualTo("clinic.area", selectedArea)
            .get()
            .await()

        Log.e("FirestoreQuery", "Documents found: ${snapshot.documents.size}")

        Result.success(snapshot.documents.mapNotNull { it.toObject(Dentist::class.java) })
    } catch (t: Throwable) {
        Log.e("FirestoreQuery", "Error fetching dentists: ${t.localizedMessage}")
        Result.failure(t)
    }

    override suspend fun fetchAvailableAppointments(dentistId: String): Result<List<Appointment>> =
        try {
            val snapshot =
                dentistCollection.document(dentistId)
                    .collection(FirebaseConstants.APPOINTMENTS)
                    .orderBy(FirebaseConstants.TIMESTAMP).get().await()
            val appointments = snapshot.documents.mapNotNull {
                it.toObject(Appointment::class.java)?.copy(id = it.id)
            }
            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun bookAppointment(appointment: Appointment): Result<String> = try {
        val appointmentRef = dentistCollection
            .document(appointment.dentistId!!)
            .collection(FirebaseConstants.APPOINTMENTS)
            .document(appointment.id!!)

        val snapshot = appointmentRef.get().await()

        if (snapshot.exists()) {
            val currentStatus = snapshot.getString("status")
            if (currentStatus == AppointmentStatus.BOOKED.name.lowercase()) {
                Result.failure(Throwable(message = AppointmentBookResponse.BOOKED_ALREADY.name.lowercase()))
            } else {
                appointmentRef.update(
                    mapOf(
                        "id" to appointment.id,
                        "patientName" to appointment.patientName,
                        "patientId" to appointment.patientId,
                        "status" to AppointmentStatus.BOOKED.name.lowercase()
                    )
                ).await()
                Result.success(AppointmentBookResponse.BOOKED_SUCCESSFULLY.name.lowercase())
            }
        } else {
            Result.failure(Throwable(message = AppointmentBookResponse.APPOINTMENT_NOT_FOUND.name.lowercase()))
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun fetchDentistAppointments(dentistId: String): Result<List<Appointment>> {
        try {
            val appointmentsCollection = dentistCollection.document(dentistId)
                .collection(FirebaseConstants.APPOINTMENTS)

            val querySnapshot = appointmentsCollection.get().await()
            Log.e("FirestoreQuery", "Documents found: ${querySnapshot.documents.size}")
            val appointments = querySnapshot.documents.mapNotNull {
                it.toObject(Appointment::class.java)?.copy(id = it.id)
            }
            Log.e("FirestoreQuery", "Appointments fetched: $appointments")
            return Result.success(appointments)
        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }
}