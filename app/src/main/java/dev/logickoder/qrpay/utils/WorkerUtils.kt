package dev.logickoder.qrpay.utils

import androidx.work.*
import java.util.concurrent.TimeUnit

inline fun <reified T : ListenableWorker> createWorker() = PeriodicWorkRequestBuilder<T>(
    20, TimeUnit.SECONDS
).setConstraints(
    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
).setBackoffCriteria(
    BackoffPolicy.LINEAR,
    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
    TimeUnit.MILLISECONDS,
).build()
