package com.nca.yourdentist.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dentist(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("email")
    var email: String,
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("clinicPhoneNumber")
    var clinicPhoneNumber: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("city")
    val city: Int? = null,
    @SerializedName("area")
    val area: Int? = null,
    @SerializedName("rate")
    val rate: Float? = null,
    @SerializedName("profilePicture")
    val profilePicture: String? = null,
    @SerializedName("location")
    val location: String? = null
) : Parcelable {
    constructor() : this(
        null, null, "",
        null, null,
        null, null, null,
        null, null, null
    )
}
