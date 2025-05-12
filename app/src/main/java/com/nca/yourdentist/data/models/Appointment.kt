package com.nca.yourdentist.data.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.nca.yourdentist.domain.models.AppointmentStatus
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Appointment(
    val id: String? = null,
    val dentistId: String? = null,
    val dentistName: String? = null,
    var patientId: String? = null,
    var patientName: String? = null,
    val timestamp: Timestamp? = null,
    var report: Report? = null,
    var status: String = AppointmentStatus.AVAILABLE.name.lowercase()
) : Parcelable

@Parcelize
data class Report(
    val id: String = UUID.randomUUID().toString(),
    val date: Timestamp = Timestamp.now(),
    val originalImageUrl: String? = null,
    val detectedCariesImageUrl: String? = null,
    val questionnaireResult: List<Question>? = null,
    val notes: String? = null,
) : Parcelable

@Parcelize
data class Question(
    val title: String,
    var answer: String = ""
) : Parcelable