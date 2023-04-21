package dev.logickoder.qrpay.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.logickoder.qrpay.model.Transaction
import dev.logickoder.qrpay.model.TransactionDescription
import dev.logickoder.qrpay.model.TransactionType
import java.time.LocalDateTime

@Entity(tableName = "transaction")
class TransactionEntity(
    @PrimaryKey val id: String = "",

    val type: TransactionType = TransactionType.Transfer,

    val description: TransactionDescription = TransactionDescription(),

    val amount: Float = 0f,

    val time: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?) = (other as? TransactionEntity)?.id == id
    override fun hashCode() = id.hashCode()
}

internal fun Transaction.toEntity() = TransactionEntity(
    id = id,
    type = type,
    description = description,
    amount = amount,
    time = time,
)

internal fun TransactionEntity.toTransaction() = Transaction(
    id = id,
    type = type,
    description = description,
    amount = amount,
    time = time,
)