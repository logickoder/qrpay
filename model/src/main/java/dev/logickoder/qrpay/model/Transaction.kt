package dev.logickoder.qrpay.model

import dev.logickoder.qrpay.model.serializer.BigDecimalSerializer
import dev.logickoder.qrpay.model.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Represents a transaction entity that is serializable and mapped to a database table using JPA.
 *
 * @property id The ID of the transaction, automatically generated using UUID strategy.
 * @property type The type of the transaction, represented by [TransactionType] enum.
 * @property description The description of the transaction.
 * @property amount The amount of the transaction.
 * @property time The time of the transaction
 */
@Serializable
data class Transaction(
    val id: String = "",

    val type: TransactionType = TransactionType.Transfer,

    val description: TransactionDescription = TransactionDescription(),

    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal = BigDecimal.ZERO,

    @Serializable(with = LocalDateTimeSerializer::class)
    val time: LocalDateTime = LocalDateTime.now(),
)

/**
 * Represents the types of transactions.
 */
enum class TransactionType {
    Transfer
}

/**
 * Data class representing a transaction description.
 *
 * @property value Description of the transaction. Default value is an empty string.
 * @property sender Sender of the transaction. Default value is null.
 * @property recipient Recipient of the transaction. Default value is null.
 */
@Serializable
data class TransactionDescription(
    val value: String = "",

    val sender: String? = null,

    val recipient: String? = null,
)