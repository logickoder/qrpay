package dev.logickoder.qrpay.app.data.sync

import android.util.Log
import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.repository.UserRepository
import javax.inject.Inject


class UserSync @Inject constructor(
    userRepository: UserRepository
) : Sync(userRepository) {

    override suspend fun work(id: String) {
        when (val result = userRepository.login(id)) {
            is ResultWrapper.Success ->
                Log.d(TAG, "Refreshed User: ${result.data.id} from server")

            is ResultWrapper.Failure ->
                Log.e(TAG, "Failed to refresh user from server")

            ResultWrapper.Loading -> {
                // Do nothing
            }
        }
    }

    companion object {
        val TAG = UserSync::class.simpleName
    }
}