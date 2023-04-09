package dev.logickoder.qrpay

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.logickoder.qrpay.app.data.local.TransactionStore
import dev.logickoder.qrpay.app.data.model.Transaction

@Database(entities = [Transaction::class], version = 1)
abstract class QrPayDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionStore
}
