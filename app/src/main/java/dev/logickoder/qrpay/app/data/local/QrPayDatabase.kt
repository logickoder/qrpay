package dev.logickoder.qrpay.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TransactionEntity::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class QrPayDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionStore
}
