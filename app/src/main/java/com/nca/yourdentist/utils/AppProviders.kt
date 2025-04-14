package com.nca.yourdentist.utils

import android.graphics.Bitmap
import com.nca.yourdentist.data.model.Dentist
import com.nca.yourdentist.data.model.FinalResult
import com.nca.yourdentist.data.model.Patient

object AppProviders {
    var patient: Patient? = null
    var patientQRCodeBitmap: Bitmap? = null
    var finalResult: FinalResult? = null

    var dentist: Dentist? = null
}