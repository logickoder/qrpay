package dev.logickoder.qrpay.app.data.sync

import dev.logickoder.qrpay.app.data.remote.ResultWrapper

interface Sync {
    /**
     * Launches the sync task
     */
    suspend fun sync(): ResultWrapper<String>
}