package dev.logickoder.qrpay.app.data.sync

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class SyncLauncher @Inject constructor(
    private val transactionsSync: TransactionsSync,
    private val userSync: UserSync,
) {
    private val scope = MainScope() + Dispatchers.Default

    private val rerunDelay = 20L.seconds

    fun launch() {
        scope.launch {
            while (true) {
                coroutineScope {
                    launch {
                        userSync.sync()
                    }
                    launch {
                        transactionsSync.sync()
                    }
                }
                delay(rerunDelay)
            }
        }
    }
}