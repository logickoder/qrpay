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
}

class TransactionsStoreImpl @Inject constructor(
    private val dao: TransactionStore
) : DataStoreManager<List<@JvmSuppressWildcards Transaction>> {
    override suspend fun save(data: List<Transaction>) = dao.save(*data.toTypedArray())
    override fun get(): Flow<List<Transaction>?> = dao.get()
}
