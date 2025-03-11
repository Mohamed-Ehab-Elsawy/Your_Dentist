package com.nca.yourdentist.data.model.requests

import com.nca.yourdentist.data.model.Patient

data class AuthRequest(
    val email: String,
    val password: String,
    var patient: Patient? = null
)
