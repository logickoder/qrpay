package dev.logickoder.qrpay.transaction

import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<Transaction, String>