package dev.logickoder.qrpay.transaction

import dev.logickoder.qrpay.user.User
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<Transaction, String> {
    fun findByUser(user: User): List<Transaction>
}