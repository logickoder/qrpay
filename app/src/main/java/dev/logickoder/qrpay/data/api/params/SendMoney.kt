package dev.logickoder.qrpay.data.api.params


import com.google.gson.annotations.SerializedName

data class SendMoney(
    val amount: String,
    val narration: String,
    @SerializedName("QrPayUid")
    val qrPayUid: String,
    val recipientUid: String
)