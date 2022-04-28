package dev.logickoder.qrpay.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.logickoder.qrpay.data.repository.TransactionsRepo
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.utils.RERUN_DELAY
import dev.logickoder.qrpay.utils.ResultWrapper
import dev.logickoder.qrpay.utils.createWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltWorker
class TransactionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val transactionsRepo: TransactionsRepo,
    private val userRepo: UserRepo,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val result = suspendCoroutine<Result> { continuation ->
            launch {
                // get the user id first
                userRepo.currentUser.collect { user ->
                    if (user == null)
                        continuation.resume(Result.failure())
                    else {
                        val result = transactionsRepo.fetchTransactions(user.id)
                        continuation.resume(
                            when (result) {
                                is ResultWrapper.Success -> {
                                    Log.d(TAG, "Refreshed transactions from server")
                                    Result.success()
                                }
                                else -> Result.failure()
                            }
                        )
                    }
                }
            }
        }
        // re-run the work
        applicationContext.createWork<TransactionWorker>(RERUN_DELAY)
        return@withContext result
    }


    companion object {
        val TAG = TransactionWorker::class.simpleName
    }
}
