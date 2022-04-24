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
import dev.logickoder.qrpay.utils.ResultWrapper
import dev.logickoder.qrpay.utils.createWorker
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

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine<Result> { continuation ->
                launch {
                    // get the user id first
                    userRepo.currentUser.collect { user ->
                        user ?: continuation.resume(Result.failure())
                        val result = transactionsRepo.fetchTransactions(user!!.id)
                        continuation.resume(
                            when (result) {
                                is ResultWrapper.Success -> {
                                    Log.d(TAG, "Refreshed transactions from server")
                                    Result.success()
                                }
                                is ResultWrapper.Failure -> Result.failure()
                                is ResultWrapper.Loading -> Result.retry()
                            }
                        )
                    }
                }
            }
        }
    }

    companion object {
        val TAG = TransactionWorker::class.simpleName
        val WORKER = createWorker<TransactionWorker>()
    }
}
