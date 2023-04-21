package dev.logickoder.qrpay

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.logickoder.qrpay.app.data.local.TransactionEntity
import dev.logickoder.qrpay.app.data.local.TransactionStore

@Database(entities = [TransactionEntity::class], version = 1)
abstract class QrPayDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionStore
}
