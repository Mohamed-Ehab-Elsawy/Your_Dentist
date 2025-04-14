package com.nca.yourdentist.data.model.requests

import com.google.firebase.Timestamp
import com.nca.yourdentist.data.model.FinalResult

data class AppointmentRequest(
    val patientId: String? = null,
    val dentistId: String? = null,
    val finalRequest: FinalResult? = null,
    val date: String? = null,
    val time: String? = null,
    val status: String = "Upcoming",
    val bookedAt: Timestamp = Timestamp.now()
)