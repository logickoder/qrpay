package dev.logickoder.qrpay.data.models

import java.time.LocalDateTime

data class Transaction(
    val date: LocalDateTime,
    val from: User,
    val to: User,
    val comment: String,
    val amount: Double,
)
