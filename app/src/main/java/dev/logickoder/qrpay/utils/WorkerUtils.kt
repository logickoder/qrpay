package dev.logickoder.qrpay.utils

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

inline fun <reified T : ListenableWorker> Context.createWork() = workManager.enqueueUniqueWork(
    T::class.simpleName.toString(),
    ExistingWorkPolicy.KEEP,
    OneTimeWorkRequestBuilder<T>().build()
)

val Context.workManager: WorkManager get() = WorkManager.getInstance(this)

const val RERUN_DELAY = 20L
