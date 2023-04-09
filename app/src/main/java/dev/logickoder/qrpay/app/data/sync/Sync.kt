package dev.logickoder.qrpay.app.data.sync

import dev.logickoder.qrpay.app.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

abstract class Sync(
    protected val userRepository: UserRepository,
) {
    private var id: String? = null

    /**
     * Performs the actual sync work
     */
    abstract suspend fun work(id: String)

    /**
     * Launches the sync task
     */
    suspend fun sync() = withContext(Dispatchers.IO) {
        launch {
            userRepository.currentUser.collect { user -> id = user?.id }
        }
        while (true) {
            id?.let { id -> work(id) }
            delay(RERUN_DELAY.seconds)
        }
    }

    companion object {
        const val RERUN_DELAY = 20L
    }
}