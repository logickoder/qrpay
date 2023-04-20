package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.app.data.converter.TransactionDescriptionConverter
import dev.logickoder.qrpay.model.Transaction
import dev.logickoder.qrpay.model.TransactionDescription
import dev.logickoder.qrpay.model.TransactionType
import dev.logickoder.qrpay.user.UserEntity
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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
internal data class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    val type: TransactionType = TransactionType.Transfer,

    @Convert(converter = TransactionDescriptionConverter::class)
    val description: TransactionDescription = TransactionDescription(),

    val amount: BigDecimal = BigDecimal.ZERO,

    val time: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity? = null,
)

internal fun TransactionEntity.toTransaction() = Transaction(
    id = id,
    type = type,
    description = description,
    amount = amount,
    time = time,
)