package dev.logickoder.qrpay.utils

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

inline fun <reified T : ListenableWorker> Context.createWork(
    delay: Long = 0L
) = WorkManager.getInstance(this).enqueueUniqueWork(
    T::class.simpleName.toString(),
    ExistingWorkPolicy.KEEP,
    OneTimeWorkRequestBuilder<T>().setConstraints(
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    ).setBackoffCriteria(
        BackoffPolicy.LINEAR,
        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
        TimeUnit.MILLISECONDS,
    ).run {
        if (delay > 0) setInitialDelay(delay, TimeUnit.SECONDS) else this
    }.build()
)

const val RERUN_DELAY = 30L
