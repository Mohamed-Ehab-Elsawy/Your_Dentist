package com.nca.yourdentist.data.models.requests

import com.nca.yourdentist.data.models.users.Patient

data class AuthRequest(
    val email: String,
    val password: String,
    var patient: Patient? = null
)