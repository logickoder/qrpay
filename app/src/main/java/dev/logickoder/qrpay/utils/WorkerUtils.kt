package dev.logickoder.qrpay.utils

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.delay

suspend inline fun <reified T : ListenableWorker> Context.createWork(
    delay: Long = 0L
): Operation {
    delay(delay * 1000)
    return WorkManager.getInstance(this).enqueue(
        OneTimeWorkRequestBuilder<T>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()
    )
}

const val RERUN_DELAY = 30L
