package com.nca.yourdentist.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nca.yourdentist.data.notification.NotificationHelper

class ToothbrushReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val helper = NotificationHelper(applicationContext)
        helper.showNotification("Time to brush!", "Keep your teeth clean 🪥")
        return Result.success()
    }
}