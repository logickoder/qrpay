package dev.logickoder.qrpay.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
)