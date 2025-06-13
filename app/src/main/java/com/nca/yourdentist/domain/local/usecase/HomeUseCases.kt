package com.nca.yourdentist.domain.local.usecase

import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.models.users.Patient
import com.nca.yourdentist.domain.local.repository.HomeRepository
import com.nca.yourdentist.domain.local.repository.ReminderScheduler

class FetchLocalPatientDataUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): Patient = homeRepository.fetchPatientData()
}

class PutLocalPatientDataUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(patient: Patient) = homeRepository.putPatient(patient)
}

class FetchLocalDentistDataUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): Dentist = homeRepository.fetchDentistData()
}

class FetchReminderStateUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): Boolean = homeRepository.fetchReminderState()
}

class PutReminderStateUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(value: Boolean) = homeRepository.putReminderState(value)
}

class ScheduleToothbrushReminderUseCase(private val reminderScheduler: ReminderScheduler) {
    operator fun invoke() = reminderScheduler.schedule()
}