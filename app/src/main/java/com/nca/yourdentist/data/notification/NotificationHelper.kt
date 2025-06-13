package com.nca.yourdentist.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.nca.yourdentist.R

class NotificationHelper(private val context: Context) {
    private val reportsChannelId = "report_channel"
    private val reportsChannelName = "Reports"
    private val toothbrushChannelId = "toothbrush_channel"
    private val toothbrushChannelName = "Tooth brushes reminder"
    private val generalChannelId = "general_channel"
    private val generalChannelName = "General"

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            val generalChannel = NotificationChannel(
                generalChannelId,
                generalChannelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val reportChannel = NotificationChannel(
                reportsChannelId,
                reportsChannelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val toothbrushChannel = NotificationChannel(
                toothbrushChannelId,
                toothbrushChannelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager?.createNotificationChannel(generalChannel)
            notificationManager?.createNotificationChannel(reportChannel)
            notificationManager?.createNotificationChannel(toothbrushChannel)
        }
    }

    fun showReminder() {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, toothbrushChannelId)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Time to brush!")
            .setContentText("Keep your teeth clean 🪥")
            .setAutoCancel(true)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    fun sendGeneralNotification(title: String, message: String) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, generalChannelId)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    fun sendReportNotification(title: String, message: String) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, reportsChannelId)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}