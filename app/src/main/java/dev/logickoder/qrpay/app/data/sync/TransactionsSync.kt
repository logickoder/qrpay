package dev.logickoder.qrpay.app.data.sync

import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.repository.TransactionsRepository
import io.github.aakira.napier.Napier
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TransactionsSync @Inject constructor(
    private val transactions: TransactionsRepository,
) : Sync {

    override suspend fun sync(): ResultWrapper<String> {
        val result = transactions.fetchTransactions()
        when (result) {
            is ResultWrapper.Success -> {
                Napier.d("Refreshed transactions from server")
            }

            is ResultWrapper.Failure -> Napier.e(
                "Failed to refresh transactions from server",
                result.error
            )

            ResultWrapper.Loading -> {
                // Do nothing
            }
        }
        return result
    }
}