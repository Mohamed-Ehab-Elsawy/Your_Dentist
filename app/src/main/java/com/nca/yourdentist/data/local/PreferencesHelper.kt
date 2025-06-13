package com.nca.yourdentist.data.local

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.models.users.Patient

class PreferencesHelper(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val TAG = "Preferences Helper"
        const val PATIENT = "patient"
        const val DENTIST = "dentist"
        const val REMINDER_STATE = "reminder state"
        const val CURRENT_LANGUAGE = "current language"
        const val QR_CODE = "qr code: "
        const val NOTIFICATION_ENABLED = "notification enabled"
    }

    private val gson = Gson()

    fun putString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun fetchString(key: String, default: String = ""): String =
        sharedPreferences.getString(key, default) ?: ""

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun fetchBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    fun clearString(key: String) {
        sharedPreferences.edit { putString(key, "") }
    }

    fun putPatient(patient: Patient) {
        val patientDataJson = gson.toJson(patient)
        putString(PATIENT, patientDataJson)
        Log.e(TAG, "Patient saved, id: ${patient.id}, name: ${patient.name}")
    }

    fun fetchPatient(): Patient {
        val json = fetchString(PATIENT)
        return if (json.isNotEmpty()) {
            val patient = gson.fromJson(json, Patient::class.java)
            Log.e(TAG, "Patient fetched, id:${patient.id}, name: ${patient.name}")
            patient
        } else Patient()
    }

    fun putDentist(dentist: Dentist) {
        val dentistDataJson = gson.toJson(dentist)
        putString(DENTIST, dentistDataJson)
        Log.e(TAG, "Dentist saved, id: ${dentist.id}, name: ${dentist.name}")
    }

    fun fetchDentist(): Dentist {
        val json = fetchString(DENTIST)
        return if (json.isNotEmpty()) {
            val dentist = gson.fromJson(json, Dentist::class.java)
            Log.e(TAG, "Dentist fetched, id:${dentist.id}, name: ${dentist.name}")
            dentist
        } else Dentist()
    }

    fun clearData() {
        sharedPreferences.edit {
            clearString(PATIENT)
            clearString(DENTIST)
            Log.e(TAG, "Data cleared")
        }
    }
}