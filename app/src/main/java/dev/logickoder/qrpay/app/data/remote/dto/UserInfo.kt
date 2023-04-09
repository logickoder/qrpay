package dev.logickoder.qrpay.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("QrPayUid")
    val qrPayUid: String
)