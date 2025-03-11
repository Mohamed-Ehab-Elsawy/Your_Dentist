package com.nca.yourdentist.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var gender: String? = null,
    var phoneNumber: String? = null,
    var birthDate: String? = null,
    val appointments: List<String>? = null,
    val type: String = "patient"
) : Parcelable
