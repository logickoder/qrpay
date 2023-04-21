package dev.logickoder.qrpay.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val firstname: String = "",
    val lastname: String = "",
    val username: String = "",
    val password: String? = null,
)