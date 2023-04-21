package dev.logickoder.qrpay.model

import kotlinx.serialization.Serializable

@Serializable
class Response<T>(
    val success: Boolean = true,
    val message: String,
    val data: T?,
)

fun <T> Response<T>.isSuccessful() = success && data != null