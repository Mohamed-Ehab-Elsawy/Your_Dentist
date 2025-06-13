package com.nca.yourdentist.data.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.nca.yourdentist.domain.models.AppointmentStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointment(
    val id: String? = null,
    var dentistId: String? = null,
    var dentistName: String? = null,
    var patientId: String? = null,
    var patientName: String? = null,
    val timestamp: Timestamp? = null,
    var report: Report? = null,
    var status: String = AppointmentStatus.AVAILABLE.name.lowercase()
) : Parcelable

@Parcelize
data class Report(
    val id: String? = null,
    var creationTime: Timestamp = Timestamp.now(),
    var dentistId: String? = null,
    var dentistName: String? = null,
    var uploadedImageUrl: String? = null,
    var detectedCariesImageUrl: String? = null,
    var questionnaire: List<Questionnaire>? = null,
    var notes: String? = null,
    var notified: Boolean = false,
    var read: Boolean = false,
    var dentistRated: Boolean = false
) : Parcelable

@Parcelize
data class Questionnaire(
    val title: String = "",
    var answer: String = ""
) : Parcelable