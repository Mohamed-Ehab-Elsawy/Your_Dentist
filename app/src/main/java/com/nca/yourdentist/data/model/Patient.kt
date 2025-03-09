package com.nca.yourdentist.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Patient(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var gender: String? = null,
    var phoneNumber: String? = null,
    var birthDate: Date? = null,
    val appointments: List<String>? = null,
    val type: String = "patient"
) : Parcelable
