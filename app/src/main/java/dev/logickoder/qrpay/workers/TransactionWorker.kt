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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

@HiltWorker
class TransactionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val transactionsRepo: TransactionsRepo,
    private val userRepo: UserRepo,
) : CoroutineWorker(context, workerParams) {

    private var id: String? = null

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        launch {
            userRepo.currentUser.collect { user -> id = user?.id }
        }
        while (true) {
            id?.let { id ->
                when (transactionsRepo.fetchTransactions(id)) {
                    is ResultWrapper.Success ->
                        Log.d(TAG, "Refreshed transactions from server")
                    is ResultWrapper.Failure ->
                        Log.e(TAG, "Failed to refresh transactions from server")
                    ResultWrapper.Loading -> {/* Do nothing */
                    }
                }
            }
            delay(RERUN_DELAY.seconds)
        }
        return@withContext Result.success()
    }

    companion object {
        val TAG = TransactionWorker::class.simpleName
    }
}
