package dev.logickoder.qrpay.app.data.remote.params

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String
)

@Serializable
data class RegisterResponse(
    val `data`: UserInfo,
    val error: Boolean,
    val message: String
)
