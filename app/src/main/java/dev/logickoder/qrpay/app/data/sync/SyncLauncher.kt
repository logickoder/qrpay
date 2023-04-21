package dev.logickoder.qrpay.app.data.sync

import kotlinx.coroutines.MainScope
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

    override suspend fun sync() {
        coroutineScope {
            launch {
                userSync.sync()
            }
            launch {
                transactionsSync.sync()
            }
        }
    }
}