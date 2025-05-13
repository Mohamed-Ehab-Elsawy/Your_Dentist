package com.nca.yourdentist.data.scheduler

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nca.yourdentist.domain.local.repository.ReminderScheduler
import com.nca.yourdentist.worker.ToothbrushReminderWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit

class ReminderSchedulerImpl(
    private val context: Context
) : ReminderScheduler {

    override fun schedule() {
        val now = Calendar.getInstance()
        val firstReminder = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 5)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            if (before(now)) {
                while (before(now)) {
                    add(Calendar.HOUR_OF_DAY, 6)
                }
            }
        }

        val initialDelay = firstReminder.timeInMillis - now.timeInMillis

        val workRequest = PeriodicWorkRequestBuilder<ToothbrushReminderWorker>(6, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "ToothbrushReminder",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}