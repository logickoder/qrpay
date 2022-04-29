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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

@HiltWorker
class UserWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repo: UserRepo,
) : CoroutineWorker(context, workerParams) {

    private var id: String? = null

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        launch {
            repo.currentUser.collect { user -> id = user?.id }
        }
        while (true) {
            id?.let { id ->
                when (val result = repo.login(id)) {
                    is ResultWrapper.Success ->
                        Log.d(TAG, "Refreshed User: ${result.data.id} from server")
                    is ResultWrapper.Failure ->
                        Log.e(TAG, "Failed to refresh user from server")
                    ResultWrapper.Loading -> { /* Do nothing */
                    }
                }
            }
            delay(RERUN_DELAY.seconds)
        }
        return@withContext Result.success()
    }

    companion object {
        val TAG = UserWorker::class.simpleName
    }
}
