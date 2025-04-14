package com.nca.yourdentist.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemNotification(
    val id: Int,
    val title: String,
    val description: String
) : Parcelable
