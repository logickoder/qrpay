package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.user.UserEntity
import org.springframework.data.repository.CrudRepository

internal interface TransactionRepository : CrudRepository<TransactionEntity, String> {
    fun findByUser(user: UserEntity): List<TransactionEntity>
}