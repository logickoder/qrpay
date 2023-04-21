package dev.logickoder.qrpay.app.data.sync

import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.repository.TransactionsRepository
import io.github.aakira.napier.Napier
import javax.inject.Inject


class TransactionsSync @Inject constructor(
    private val transactions: TransactionsRepository,
) : Sync {

    override suspend fun sync() {
        when (val result = transactions.fetchTransactions()) {
            is ResultWrapper.Success -> Napier.d("Refreshed transactions from server")

            is ResultWrapper.Failure -> Napier.e(
                "Failed to refresh transactions from server",
                result.error
            )

            ResultWrapper.Loading -> {
                // Do nothing
            }
        }
    }
}