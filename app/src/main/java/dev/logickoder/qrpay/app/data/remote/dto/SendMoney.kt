package dev.logickoder.qrpay.app.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendMoney(
    val amount: String,
    val narration: String,
    @SerialName("QrPayUid")
    val qrPayUid: String,
    val recipientUid: String
)