package dev.logickoder.qrpay.data.repository

import dev.logickoder.qrpay.data.api.QrPayApi
import dev.logickoder.qrpay.data.api.params.RegisterRequest
import dev.logickoder.qrpay.data.api.params.UserInfo
import dev.logickoder.qrpay.data.model.User
import dev.logickoder.qrpay.data.store.DataStoreManager
import dev.logickoder.qrpay.utils.ResultWrapper
import dev.logickoder.qrpay.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    local: DataStoreManager<User>,
    remote: QrPayApi,
) : UserRepo(local, remote) {

    override val currentUser: Flow<User?>
        get() = local.get()

    override suspend fun login(qrPayUid: String): ResultWrapper<User> {
        val result = safeApiCall(Dispatchers.IO) {
            remote.login(UserInfo(qrPayUid))
        }
        return when (result) {
            is ResultWrapper.Success -> if (!result.data.error) {
                // save the user to the local storage
                val user = result.data.data.user
                local.save(user)
                ResultWrapper.Success(user)
            } else ResultWrapper.Failure(result.data.message)
            else -> result as ResultWrapper<User>
        }
    }

    override suspend fun register(name: String): ResultWrapper<User> {
        val result = safeApiCall(Dispatchers.IO) {
            remote.register(RegisterRequest(name))
        }
        return when (result) {
            is ResultWrapper.Success -> if (!result.data.error) {
                // login with the user
                login(result.data.data.qrPayUid)
            } else ResultWrapper.Failure(result.data.message)
            // retrieve the user from the local source
            else -> result as ResultWrapper<User>
        }
    }
}
