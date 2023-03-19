package dev.logickoder.qrpay.data.model

import dev.logickoder.qrpay.utils.Amount
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val id: String,
    val balance: Amount,
    val demoBalance: Amount = 50_000.0,
    val transactions: Int,
    val currency: String,
)
