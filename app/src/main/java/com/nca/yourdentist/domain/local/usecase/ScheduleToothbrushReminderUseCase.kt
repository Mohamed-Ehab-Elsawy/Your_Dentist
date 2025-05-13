package com.nca.yourdentist.domain.local.usecase

import com.nca.yourdentist.domain.local.repository.ReminderScheduler

class ScheduleToothbrushReminderUseCase(
    private val reminderScheduler: ReminderScheduler
) {
    fun execute() {
        reminderScheduler.schedule()
    }
}