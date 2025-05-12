package com.nca.yourdentist.data.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class AppNotification(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val body: String = "",
    var isRead: Boolean = false,
    val date: Timestamp = Timestamp.now()
) : Parcelable