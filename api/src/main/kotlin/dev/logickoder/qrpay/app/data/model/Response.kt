package dev.logickoder.qrpay.app.data.model

import kotlinx.serialization.Serializable

typealias ResponseData = Map<String, Any?>

@Serializable
class Response<T>(
    val data: T,
    val message: String,
    val success: Boolean = true,
)