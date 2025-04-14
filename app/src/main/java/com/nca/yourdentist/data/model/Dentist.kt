package com.nca.yourdentist.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dentist(
    @SerializedName("createdAt") val createdAt: Timestamp = Timestamp.now(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("city") val city: Int? = null,
    @SerializedName("area") val area: Int? = null,
    @SerializedName("rate") val rate: Float? = 0f,
    @SerializedName("sessionPrice") val sessionPrice: String? = null,
    @SerializedName("profileImage") val profileImage: String? = null,
    @SerializedName("clinicLocation") val clinicLocation: String? = null,
    @SerializedName("clinicPhoneNumber") var clinicPhoneNumber: String? = null,
    @SerializedName("type") val type: String? = null
) : Parcelable