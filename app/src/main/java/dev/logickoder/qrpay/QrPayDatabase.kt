package dev.logickoder.qrpay

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.logickoder.qrpay.data.model.Transaction
import dev.logickoder.qrpay.data.store.TransactionStore

@Database(entities = [Transaction::class], version = 1)
abstract class QrPayDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionStore
}
