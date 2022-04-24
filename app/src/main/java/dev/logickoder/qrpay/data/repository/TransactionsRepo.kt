package dev.logickoder.qrpay.data.repository

import dev.logickoder.qrpay.data.api.QrPayApi
import dev.logickoder.qrpay.data.model.Transaction
import dev.logickoder.qrpay.data.store.DataStoreManager
import dev.logickoder.qrpay.utils.Amount
import dev.logickoder.qrpay.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

/**
 * Handles operations regarding to transactions
 */
abstract class TransactionsRepo(
    protected val local: DataStoreManager<List<Transaction>>,
    remote: QrPayApi
) : Repository(remote) {

    /**
     * Retrieve transactions saved on the device
     */
    abstract val transactions: Flow<List<Transaction>>

    /**
     * Send [amount] to user with user id [recipientUid]
     */
    abstract suspend fun sendMoney(
        amount: Amount,
        narration: String,
        qrPayUid: String,
        recipientUid: String
    ): ResultWrapper<Unit>

    /**
     * Retrieve all transactions from the remote server with id [qrPayUid]
     */
    abstract suspend fun fetchTransactions(qrPayUid: String): ResultWrapper<List<Transaction>>
}
