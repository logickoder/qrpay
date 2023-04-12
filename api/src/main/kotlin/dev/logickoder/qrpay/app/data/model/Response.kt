package dev.logickoder.qrpay.app.data.model

import kotlinx.serialization.Serializable

typealias ResponseData = Map<String, Any?>

@Serializable
class Response<T>(
    val success: Boolean = true,
    val message: String,
    val data: T,
)