package dev.logickoder.qrpay.data.api.params

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val name: String
)

data class RegisterResponse(
    val `data`: Data,
    val error: Boolean,
    val message: String
) {
    data class Data(
        @SerializedName("QrPayUid")
        val qrPayUid: String
    )
}
