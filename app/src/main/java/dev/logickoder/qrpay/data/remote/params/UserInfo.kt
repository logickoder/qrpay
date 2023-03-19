package dev.logickoder.qrpay.data.remote.params

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("QrPayUid")
    val qrPayUid: String
)