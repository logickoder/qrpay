package dev.logickoder.qrpay.app.data.sync

interface Sync {
    /**
     * Launches the sync task
     */
    suspend fun sync()
}