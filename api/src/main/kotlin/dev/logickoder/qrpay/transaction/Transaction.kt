package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.app.data.converter.BigDecimalSerializer
import dev.logickoder.qrpay.app.data.converter.LocalDateTimeSerializer
import dev.logickoder.qrpay.app.data.converter.TransactionDescriptionConverter
import dev.logickoder.qrpay.user.User
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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
 * @property user The user associated with the transaction using Many-to-One relationship.
 */
@Entity
@Serializable
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    val type: TransactionType = TransactionType.Transfer,

    @Convert(converter = TransactionDescriptionConverter::class)
    val description: TransactionDescription = TransactionDescription(),

    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal = BigDecimal.ZERO,

    @Serializable(with = LocalDateTimeSerializer::class)
    val time: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null,
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
    @Column(name = "description")
    val value: String = "",

    val sender: String? = null,

    val recipient: String? = null,
)