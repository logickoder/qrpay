package dev.logickoder.qrpay.app.data.sync

import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncLauncher @Inject constructor(
    private val transactionsSync: TransactionsSync,
    private val userSync: UserSync,
) : Sync {

    init {
        MainScope().launch {
            sync()
        }
    }

    override suspend fun sync(): ResultWrapper<String> {
        val jobs = coroutineScope {
            listOf(
                async {
                    userSync.sync()
                },
                async {
                    transactionsSync.sync()
                }
            ).map { it.await() }
        }

        return jobs.firstOrNull {
            it is ResultWrapper.Failure
        } ?: ResultWrapper.Success("Sync successful")
    }
}