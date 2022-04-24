package dev.logickoder.qrpay.data.model

data class User(
    val name: String,
    val id: String,
    val balance: Double,
    val transactions: Int,
    val currency: String,
)