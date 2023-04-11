package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.app.data.converter.BigDecimalSerializer
import dev.logickoder.qrpay.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kotlinx.serialization.Serializable
import java.math.BigDecimal

/**
 * Represents a transaction entity that is serializable and mapped to a database table using JPA.
 *
 * @property id The ID of the transaction, automatically generated using UUID strategy.
 * @property type The type of the transaction, represented by [TransactionType] enum.
 * @property description The description of the transaction.
 * @property amount The amount of the transaction.
 * @property user The user associated with the transaction using Many-to-One relationship.
 */
@Entity
@Serializable
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    val type: TransactionType = TransactionType.Transfer,
    val description: String = "",
    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal = BigDecimal.ZERO,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null,
) {
    companion object {
        val privateFields = arrayOf("id", "user")
    }
}

/**
 * Represents the types of transactions.
 */
enum class TransactionType {
    Transfer
}