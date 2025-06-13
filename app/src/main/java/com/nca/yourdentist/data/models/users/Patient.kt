package com.nca.yourdentist.data.models.users

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.nca.yourdentist.data.models.Appointment
import com.nca.yourdentist.data.remote.utils.FirebaseConstants
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    val createdAt: Timestamp = Timestamp.now(),
    var id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val phoneNumber: String? = null,
    val birthDate: String? = null,
    val qrCode: String? = null,
    var upcomingAppointment: Appointment? = null,
    val type: String = FirebaseConstants.PATIENT
) : Parcelable