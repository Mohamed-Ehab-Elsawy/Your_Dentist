package com.nca.yourdentist.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationItem(
    val id: Int,
    val title: String,
    val description: String
) : Parcelable
