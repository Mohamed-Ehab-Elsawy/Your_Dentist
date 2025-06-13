package com.nca.yourdentist.data.remote.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.local.PreferencesHelper
import com.nca.yourdentist.data.models.AppNotification
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.remote.utils.FirebaseConstants
import com.nca.yourdentist.domain.models.AppointmentBookResponse
import com.nca.yourdentist.domain.models.AppointmentStatus
import com.nca.yourdentist.domain.remote.repository.AppointmentsRepository
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class AppointmentsRepositoryImpl(
    private val preferencesHelper: PreferencesHelper,
    firestore: FirebaseFirestore
) : AppointmentsRepository {

    private val patientCollection = firestore.collection(FirebaseConstants.PATIENT_COLLECTIONS)
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
            val snapshot = dentistCollection.document(dentistId)
                .collection(FirebaseConstants.APPOINTMENTS)
                .orderBy(FirebaseConstants.TIMESTAMP)
                .get().await()

            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val tomorrowStart = calendar.time

            val appointments = snapshot.documents.mapNotNull {
                it.toObject(Appointment::class.java)?.copy(id = it.id)
            }.filter { appointment ->
                appointment.timestamp?.toDate()?.after(tomorrowStart) == true
            }

            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
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

    override suspend fun bookNewAppointment(appointment: Appointment): Result<String> =
        try {
            val appointmentRef = dentistCollection.document(appointment.dentistId!!)
                .collection(FirebaseConstants.APPOINTMENTS).document(appointment.id!!)
            val snapshot = appointmentRef.get().await()

            if (snapshot.exists()) {
                val currentStatus = snapshot.getString("status")
                if (currentStatus == AppointmentStatus.BOOKED.name.lowercase()) {
                    Result.failure(Throwable(message = AppointmentBookResponse.BOOKED_ALREADY.name.lowercase()))
                } else {
                    appointmentRef.update(
                        mapOf(
                            "id" to appointment.id,
                            "dentistId" to appointment.dentistId,
                            "patientName" to appointment.patientName,
                            "patientId" to appointment.patientId,
                            "report" to appointment.report,
                            "report.dentistId" to appointment.dentistId,
                            "report.dentistName" to appointment.dentistName,
                            "status" to AppointmentStatus.BOOKED.name.lowercase()
                        )
                    ).await()

                    sendDentistNotification(appointment)
                    sendPatientNotification(appointment)
                    //updatePatientUpcomingAppointment(appointment)

                    Result.success(AppointmentBookResponse.BOOKED_SUCCESSFULLY.name.lowercase())
                }
            } else {
                Result.failure(Throwable(message = AppointmentBookResponse.APPOINTMENT_NOT_FOUND.name.lowercase()))
            }
        } catch (t: Throwable) {
            Log.e("FirestoreQuery", "Error booking appointment: ${t.localizedMessage}")
            Result.failure(t)
        }

    override suspend fun updateAppointmentReport(appointment: Appointment, notes: String) {
        val appointmentRef = dentistCollection.document(appointment.dentistId!!)
            .collection(FirebaseConstants.APPOINTMENTS).document(appointment.id!!)

        appointmentRef.update(
            mapOf(
                "report.notes" to notes,
                "status" to AppointmentStatus.COMPLETED.name.lowercase()
            )
        ).await()

        appointment.report?.creationTime = Timestamp.now()
        setPatientReport(appointment)
        updateDentistPatientNumber(appointment)
        updatePatientUpcomingAppointment(appointment.copy(id = null))
    }

    override suspend fun updatePatientAppointment(appointment: Appointment) {
        patientCollection.document(appointment.patientId!!)
            .update(
                mapOf("appointment" to appointment)
            ).await()
        val modifiedPatient =
            preferencesHelper.fetchPatient().copy(upcomingAppointment = appointment)
        preferencesHelper.putPatient(modifiedPatient)
    }

    private suspend fun setPatientReport(appointment: Appointment) {
        val reportDocumentRef = patientCollection.document(appointment.patientId!!)
            .collection(FirebaseConstants.REPORTS).document()
        reportDocumentRef.set(appointment.report!!.copy(id = reportDocumentRef.id)).await()
    }

    private suspend fun updatePatientUpcomingAppointment(appointment: Appointment) {
        patientCollection.document(appointment.patientId!!)
            .update(
                mapOf(
                    "upcomingAppointment" to
                            if (appointment.id != null) appointment
                            else null
                )
            ).await()
    }

    private suspend fun sendDentistNotification(appointment: Appointment) {
        val notificationRef = dentistCollection.document(appointment.dentistId!!)
            .collection(FirebaseConstants.NOTIFICATIONS)
        val dentistNotification = AppNotification(
            title = "New Appointment",
            body = "You have a new appointment with ${appointment.patientName} at ${appointment.timestamp?.toDate()}"
        )
        notificationRef.document(dentistNotification.id).set(dentistNotification).await()
    }

    private suspend fun sendPatientNotification(appointment: Appointment) {
        val notificationRef = patientCollection.document(appointment.patientId!!)
            .collection(FirebaseConstants.NOTIFICATIONS)
        val patientNotification = AppNotification(
            title = "New Appointment",
            body = "You have a new appointment with ${appointment.dentistName} at ${appointment.timestamp?.toDate()}"
        )
        notificationRef.document(patientNotification.id).set(patientNotification).await()
    }

    private suspend fun updateDentistPatientNumber(appointment: Appointment) {
        val dentistRef = dentistCollection.document(appointment.dentistId!!)
        val dentist = dentistRef.get().await().toObject(Dentist::class.java)!!
        val newNumber = dentist.patientsNumber + 1
        dentistRef.update("patientsNumber", newNumber).await()
    }
}