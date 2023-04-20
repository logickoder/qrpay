package dev.logickoder.qrpay.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
)