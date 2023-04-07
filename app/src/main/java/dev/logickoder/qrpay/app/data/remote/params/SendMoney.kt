package dev.logickoder.qrpay.app.data.remote.params


import kotlinx.serialization.SerialName

data class SendMoney(
    val amount: String,
    val narration: String,
    @SerialName("QrPayUid")
    val qrPayUid: String,
    val recipientUid: String
)