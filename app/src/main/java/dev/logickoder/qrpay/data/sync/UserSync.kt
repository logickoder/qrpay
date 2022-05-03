package dev.logickoder.qrpay.data.sync

import android.util.Log
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.utils.ResultWrapper
import javax.inject.Inject


class UserSync @Inject constructor(
    userRepo: UserRepo
) : Sync(userRepo) {

    override suspend fun work(id: String) {
        when (val result = userRepo.login(id)) {
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