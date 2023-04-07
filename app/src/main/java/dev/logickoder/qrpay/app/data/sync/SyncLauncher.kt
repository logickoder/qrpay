package dev.logickoder.qrpay.app.data.sync

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


interface SyncLauncher {
    fun launch()
}


class SyncLauncherImpl @Inject constructor(
    private val transactionsSync: TransactionsSync,
    private val userSync: UserSync,
) : SyncLauncher {
    private var isRunning = false

    @OptIn(DelicateCoroutinesApi::class)
    override fun launch() {
        if (!isRunning) {
            isRunning = true

            GlobalScope.launch {
                launch {
                    transactionsSync.sync()
                }
                launch {
                    userSync.sync()
                }
            }
        }
    }
}