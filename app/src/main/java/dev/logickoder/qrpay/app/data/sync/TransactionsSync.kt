package dev.logickoder.qrpay.app.data.sync

import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.repository.TransactionsRepository
import dev.logickoder.qrpay.app.data.repository.UserRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class TransactionsSync @Inject constructor(
    private val transactions: TransactionsRepository,
    private val users: UserRepository
) : Sync {

    override suspend fun sync() {
        val id = users.currentUser.first()?.id ?: return
        when (val result = transactions.fetchTransactions(id)) {
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