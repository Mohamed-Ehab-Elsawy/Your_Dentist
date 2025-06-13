package com.nca.yourdentist.presentation.utils

import android.graphics.Bitmap
import com.nca.yourdentist.data.models.users.Dentist
import com.nca.yourdentist.data.models.users.Patient

object Provider {
    var patient: Patient? = null
    var patientQRCodeBitmap: Bitmap? = null
    var dentist: Dentist? = null
}