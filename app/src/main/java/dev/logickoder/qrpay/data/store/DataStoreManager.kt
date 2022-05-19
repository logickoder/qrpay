package dev.logickoder.qrpay.data.store

import kotlinx.coroutines.flow.Flow

/**
 * Saves item(s) to a data store
 */
interface DataStoreManager<T> {
    /**
     * @param data the data to save
     * saves [data] in the underlying storage
     */
    suspend fun save(data: T)

    suspend fun clear()

    /**
     *
     * returns the data if it exists or null if not
     */
    fun get(): Flow<T?>
}
