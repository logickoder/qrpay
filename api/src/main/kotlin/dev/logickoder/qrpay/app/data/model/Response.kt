package dev.logickoder.qrpay.app.data.model

import kotlinx.serialization.Serializable

@Serializable
class Response<T>(
    val data: T,
    val message: String,
    val success: Boolean = true,
)