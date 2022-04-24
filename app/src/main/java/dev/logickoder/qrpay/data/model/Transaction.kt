package dev.logickoder.qrpay.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Transaction(
    @PrimaryKey val id: String,
    val date: String,
    val comment: String,
    val amount: Double,
    val type: String,
) {
    override fun equals(other: Any?) = (other as? Transaction)?.id == id
    override fun hashCode() = id.hashCode()
}
