package dev.logickoder.qrpay.data.api.params

import com.google.gson.annotations.SerializedName
import dev.logickoder.qrpay.data.model.User

data class UserInfo(
    @SerializedName("QrPayUid")
    val qrPayUid: String
)

data class LoginResponse(
    val `data`: Data,
    val error: Boolean,
    val message: String
) {
    data class Data(
        val balanceFormatted: String,
        val totalTransactions: String = "0",
        val dateFormatted: String,
        val name: String,
        @SerializedName("QrPayUid")
        val qrPayUid: String
    ) {
        val user: User
            get() = User(
                name = name,
                id = qrPayUid,
                balance = balanceFormatted.substring(1)
                    .replace(",", "")
                    .toDouble(),
                transactions = totalTransactions.toInt(),
                currency = balanceFormatted[0].toString(),
            )
    }
}

