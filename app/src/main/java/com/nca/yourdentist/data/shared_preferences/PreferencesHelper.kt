package com.nca.yourdentist.data.shared_preferences

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import androidx.core.content.edit
import com.nca.yourdentist.data.model.Dentist
import com.nca.yourdentist.data.model.Patient

class PreferencesHelper(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val PATIENT = "patient"
        const val DENTIST = "dentist"
        const val SHOW_INTRO = "show_intro"
    }

    private val gson = Gson()

    fun putString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun fetchString(key: String, default: String = ""): String =
        sharedPreferences.getString(key, default) ?: default


    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String): Boolean =
        sharedPreferences.getBoolean(key, false)


    fun clearString(key: String) {
        sharedPreferences.edit { putString(key, "") }
    }

    fun savePatient(patient: Patient) {
        val patientDataJson = gson.toJson(patient)
        putString(PATIENT, patientDataJson)
        Log.e("PreferencesHelper", "Patient saved successfully")
    }

    fun saveDentist(dentist: Dentist) {
        val dentistDataJson = gson.toJson(dentist)
        putString(DENTIST, dentistDataJson)
    }

    fun fetchPatient(): Patient {
        val json = fetchString(PATIENT)
        return if (json.isNotEmpty()) gson.fromJson(json, Patient::class.java) else Patient()
    }

    fun fetchDentist(): Dentist {
        val json = fetchString(DENTIST)
        return if (json.isNotEmpty()) gson.fromJson(json, Dentist::class.java) else Dentist()
    }
}
