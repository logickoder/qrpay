package dev.logickoder.qrpay.model

import kotlinx.serialization.Serializable

@Serializable
class Response<T>(
    val success: Boolean = true,
    val message: String,
    val data: T,
)