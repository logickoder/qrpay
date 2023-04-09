package dev.logickoder.qrpay.app.data.repository

import dev.logickoder.qrpay.app.data.local.UserStore
import dev.logickoder.qrpay.app.data.model.User
import dev.logickoder.qrpay.app.data.remote.QrPayApi
import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.remote.dto.RegisterRequest
import dev.logickoder.qrpay.app.data.remote.dto.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Handles operations relating to the user
 */
class UserRepository @Inject constructor(private val local: UserStore) {
    private val remote: QrPayApi get() = QrPayApi

    /**
     * The current logged in user
     */
    val currentUser: Flow<User?> = local.get()

    /**
     * Login with this [qrPayUid]
     */
    suspend fun login(qrPayUid: String): ResultWrapper<User> {
        return when (val result = remote.login(UserInfo(qrPayUid))) {
            is ResultWrapper.Success -> if (!result.data.error) {
                // save the user to the local storage
                val user = result.data.data.user
                local.save(user)
                ResultWrapper.Success(user)
            } else ResultWrapper.Failure(result.data.message)

            ResultWrapper.Loading -> ResultWrapper.Loading

            is ResultWrapper.Failure -> result
        }
    }

    /**
     * Register this user and generate a new user id
     */
    suspend fun register(name: String): ResultWrapper<User> {
        return when (val result = remote.register(RegisterRequest(name))) {
            is ResultWrapper.Success -> if (!result.data.error) {
                // login with the user
                login(result.data.data.qrPayUid)
            } else ResultWrapper.Failure(result.data.message)
            // retrieve the user from the local source
            ResultWrapper.Loading -> ResultWrapper.Loading

            is ResultWrapper.Failure -> result
        }
    }

    /**
     * Clear the user from the device
     */
    suspend fun clear() = local.clear()
}