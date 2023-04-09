package dev.logickoder.qrpay.app.data.repository

import dev.logickoder.qrpay.app.data.local.TransactionStore
import dev.logickoder.qrpay.app.data.model.Transaction
import dev.logickoder.qrpay.app.data.remote.QrPayApi
import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.remote.dto.SendMoney
import dev.logickoder.qrpay.app.data.remote.dto.UserInfo
import dev.logickoder.qrpay.app.utils.Amount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Handles operations regarding to transactions
 */
class TransactionsRepository @Inject constructor(
    private val local: TransactionStore,
) {

    private val remote: QrPayApi get() = QrPayApi

    /**
     * Retrieve transactions saved on the device
     */
    val transactions: Flow<List<Transaction>>
        get() = local.get().map { it ?: emptyList() }

    /**
     * Send [amount] to user with user id [recipientUid]
     */
    suspend fun sendMoney(
        amount: Amount,
        narration: String,
        qrPayUid: String,
        recipientUid: String
    ): ResultWrapper<Unit> {
        return remote.sendMoney(SendMoney(amount.toString(), narration, qrPayUid, recipientUid))
    }

    /**
     * Retrieve all transactions from the remote server with id [qrPayUid]
     */
    suspend fun fetchTransactions(qrPayUid: String): ResultWrapper<List<Transaction>> {
        return when (val result = remote.getTransactions(UserInfo(qrPayUid))) {
            is ResultWrapper.Success -> if (!result.data.error) {
                result.data.data.map { data ->
                    data.transaction
                }.let { data ->
                    local.save(*data.toTypedArray())
                    ResultWrapper.Success(data)
                }
            } else ResultWrapper.Failure(result.data.message)

            is ResultWrapper.Failure -> ResultWrapper.Failure(result.error)
            is ResultWrapper.Loading -> result
        }
    }

    /**
     * Clear all transactions on device
     */
    suspend fun clear() = local.clear()
}
