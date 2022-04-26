package dev.logickoder.qrpay.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
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
class UserWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repo: UserRepo,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine<Result> { continuation ->
                launch {
                    // get the user id first
                    repo.currentUser.collect { user ->
                        if (user == null)
                            continuation.resume(Result.failure())
                        else {
                            val result = repo.login(user.id)
                            continuation.resume(
                                when (result) {
                                    is ResultWrapper.Success -> {
                                        Log.d(TAG, "Refreshed User: ${user.id} from server")
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
        }.also {
            // re-run the work
            applicationContext.createWork<UserWorker>(RERUN_DELAY)
        }
    }

    companion object {
        val TAG = UserWorker::class.simpleName
    }
}
