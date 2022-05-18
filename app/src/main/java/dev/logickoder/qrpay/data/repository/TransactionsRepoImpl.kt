package dev.logickoder.qrpay.data.repository

import dev.logickoder.qrpay.data.api.QrPayApi
import dev.logickoder.qrpay.data.api.params.SendMoney
import dev.logickoder.qrpay.data.api.params.UserInfo
import dev.logickoder.qrpay.data.model.Transaction
import dev.logickoder.qrpay.data.store.DataStoreManager
import dev.logickoder.qrpay.utils.Amount
import dev.logickoder.qrpay.utils.ResultWrapper
import dev.logickoder.qrpay.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 *
 */
class TransactionsRepoImpl @Inject constructor(
    local: @JvmSuppressWildcards DataStoreManager<List<Transaction>>,
    remote: QrPayApi,
) : TransactionsRepo(local, remote) {

    override val transactions: Flow<List<Transaction>>
        get() = local.get().map { it ?: emptyList() }

    override suspend fun sendMoney(
        amount: Amount,
        narration: String,
        qrPayUid: String,
        recipientUid: String
    ): ResultWrapper<Unit> {
        return safeApiCall(Dispatchers.IO) {
            remote.sendMoney(SendMoney(amount.toString(), narration, qrPayUid, recipientUid))
        }
    }

    override suspend fun fetchTransactions(qrPayUid: String): ResultWrapper<List<Transaction>> {
        val result = safeApiCall(Dispatchers.IO) {
            remote.getTransactions(UserInfo(qrPayUid))
        }
        return when (result) {
            is ResultWrapper.Success -> if (!result.data.error) {
                result.data.data.map { data ->
                    data.transaction
                }.let { data ->
                    local.save(data)
                    ResultWrapper.Success(data)
                }
            } else ResultWrapper.Failure(result.data.message)
            is ResultWrapper.Failure -> ResultWrapper.Failure(result.error)
            is ResultWrapper.Loading -> result
        }
    }

    override suspend fun clear() = local.clear()
}
