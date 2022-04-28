package dev.logickoder.qrpay

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import dev.logickoder.qrpay.utils.createWork
import dev.logickoder.qrpay.workers.TransactionWorker
import dev.logickoder.qrpay.workers.UserWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class QrPayApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() = Configuration
        .Builder()
        .setWorkerFactory(workerFactory)
        .setMinimumLoggingLevel(Log.INFO)
        .build()

    override fun onCreate() {
        super.onCreate()
        runBlocking(Dispatchers.Default) {
            createWork<UserWorker>()
            createWork<TransactionWorker>()
        }
    }
}
