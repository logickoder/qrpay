package dev.logickoder.qrpay.data.sync

import android.util.Log
import dev.logickoder.qrpay.data.repository.TransactionsRepo
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.utils.ResultWrapper
import javax.inject.Inject


class TransactionsSync @Inject constructor(
    private val transactionsRepo: TransactionsRepo,
    userRepo: UserRepo
) : Sync(userRepo) {

    override suspend fun work(id: String) {
        when (transactionsRepo.fetchTransactions(id)) {
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