package dev.logickoder.qrpay.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.logickoder.qrpay.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionStore {
    @Query("SELECT * FROM `transaction`")
    fun get(): Flow<List<Transaction>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg data: Transaction)

    @Query("DELETE FROM `transaction`")
    suspend fun clear()
}
