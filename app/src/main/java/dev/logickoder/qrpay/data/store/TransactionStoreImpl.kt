package dev.logickoder.qrpay.data.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.logickoder.qrpay.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface TransactionStore {
    @Query("SELECT * FROM `transaction`")
    fun get(): Flow<List<Transaction>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg data: Transaction)

    @Query("DELETE FROM `transaction`")
    suspend fun clear()
}

class TransactionsStoreImpl @Inject constructor(
    private val table: TransactionStore
) : DataStoreManager<List<@JvmSuppressWildcards Transaction>> {
    override suspend fun save(data: List<Transaction>) = table.save(*data.toTypedArray())
    override fun get(): Flow<List<Transaction>?> = table.get()
    override suspend fun clear() = table.clear()
}
