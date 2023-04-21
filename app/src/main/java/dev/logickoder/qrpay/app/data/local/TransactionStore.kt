package dev.logickoder.qrpay.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionStore {
    @Query("SELECT * FROM `transaction`")
    fun get(): Flow<List<TransactionEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg data: TransactionEntity)

    @Query("DELETE FROM `transaction`")
    suspend fun clear()
}
