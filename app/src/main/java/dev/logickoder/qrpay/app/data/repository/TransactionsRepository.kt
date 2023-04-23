package dev.logickoder.qrpay.app.data.repository

import dev.logickoder.qrpay.app.data.local.TransactionStore
import dev.logickoder.qrpay.app.data.local.toEntity
import dev.logickoder.qrpay.app.data.local.toTransaction
import dev.logickoder.qrpay.app.data.remote.QrPayApi
import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.remote.successful
import dev.logickoder.qrpay.model.Transaction
import dev.logickoder.qrpay.model.dto.SendMoneyRequest
import dev.logickoder.qrpay.model.isSuccessful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles operations regarding to transactions
 */
@Singleton
class TransactionsRepository @Inject constructor(
    private val local: TransactionStore,
    private val remote: QrPayApi,
) {

    /**
     * Retrieve transactions saved on the device
     */
    val transactions: Flow<List<Transaction>> = local.get().map { entities ->
        entities?.map {
            it.toTransaction()
        } ?: emptyList()
    }

    /**
     * Send [amount] to [recipient]
     */
    suspend fun sendMoney(
        amount: Float,
        narration: String,
        recipient: String,
    ): ResultWrapper<String> {
        return remote.sendMoney(SendMoneyRequest(recipient, amount, narration))
            .successful { response ->
                if (response.isSuccessful()) {
                    ResultWrapper.Success(response.message)
                } else ResultWrapper.Failure(response.message)
            }
    }

    /**
     * Retrieve all transactions from server
     */
    suspend fun fetchTransactions(): ResultWrapper<String> {
        return remote.getTransactions().successful { response ->
            if (response.isSuccessful()) {
                val transactions = response.data!!.map { it.toEntity() }.toTypedArray()
                local.save(*transactions)
                ResultWrapper.Success(response.message)
            } else ResultWrapper.Failure(response.message)
        }
    }

    /**
     * Clear all transactions on device
     */
    suspend fun clear() = local.clear()
}
