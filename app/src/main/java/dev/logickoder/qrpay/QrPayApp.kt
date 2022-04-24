package dev.logickoder.qrpay

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import dev.logickoder.qrpay.workers.TransactionWorker
import dev.logickoder.qrpay.workers.UserWorker
import javax.inject.Inject

@HiltAndroidApp
class QrPayApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() = Configuration
        .Builder()
        .setWorkerFactory(workerFactory)
        .setMinimumLoggingLevel(Log.DEBUG)
        .build()

    override fun onCreate() {
        super.onCreate()
        WorkManager.getInstance(this).enqueue(UserWorker.WORKER)
        WorkManager.getInstance(this).enqueue(TransactionWorker.WORKER)
    }
}
