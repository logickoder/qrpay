package dev.logickoder.qrpay.data.sync

import android.util.Log
import dev.logickoder.qrpay.data.remote.ResultWrapper
import dev.logickoder.qrpay.data.repository.TransactionsRepository
import dev.logickoder.qrpay.data.repository.UserRepository
import javax.inject.Inject


class TransactionsSync @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    userRepository: UserRepository
) : Sync(userRepository) {

    override suspend fun work(id: String) {
        when (transactionsRepository.fetchTransactions(id)) {
            is ResultWrapper.Success ->
                Log.d(TAG, "Refreshed transactions from server")

            is ResultWrapper.Failure ->
                Log.e(TAG, "Failed to refresh transactions from server")

            ResultWrapper.Loading -> {
                // Do nothing
            }
        }
    }

    companion object {
        val TAG = TransactionsSync::class.simpleName
    }
}