package dev.logickoder.qrpay.app.data.remote.params

import dev.logickoder.qrpay.app.data.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val `data`: Data,
    val error: Boolean,
    val message: String
) {
    @Serializable
    data class Data(
        val balanceFormatted: String,
        val totalTransactions: String = "0",
        val dateFormatted: String,
        val name: String,
        @SerialName("QrPayUid")
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

