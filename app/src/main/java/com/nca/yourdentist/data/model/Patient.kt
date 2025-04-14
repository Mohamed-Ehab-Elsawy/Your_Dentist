package com.nca.yourdentist.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName
import com.nca.yourdentist.utils.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    @SerializedName("createAt") val createdAt: Timestamp = Timestamp.now(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("phoneNumber") val phoneNumber: String? = null,
    @SerializedName("birthdate") val birthDate: String? = null,
    @SerializedName("qrCode") val qrCode: String? = null,
    @SerializedName("type") val type: String = Constant.PATIENT
) : Parcelable
