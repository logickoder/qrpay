package dev.logickoder.qrpay.app.data.repository

import dev.logickoder.qrpay.app.data.local.UserStore
import dev.logickoder.qrpay.app.data.remote.QrPayApi
import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.remote.successful
import dev.logickoder.qrpay.app.data.sync.TransactionsSync
import dev.logickoder.qrpay.model.User
import dev.logickoder.qrpay.model.dto.CreateUserRequest
import dev.logickoder.qrpay.model.dto.LoginRequest
import dev.logickoder.qrpay.model.isSuccessful
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles operations relating to the user
 */
@Singleton
class UserRepository @Inject constructor(
    private val local: UserStore,
    private val remote: QrPayApi,
    private val transactions: TransactionsSync,
) {

    /**
     * The current logged in user
     */
    val currentUser: Flow<User?> = local.getUser()

    /**
     * Validate this user
     */
    suspend fun login(
        username: String,
        password: String,
    ): ResultWrapper<String> {
        // retrieve the token for this user
        return remote.login(LoginRequest(username, password)).successful { token ->
            when {
                token.isSuccessful() -> {
                    // manually clear any previous token from the auth provider
                    remote.client.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>()
                        .forEach {
                            it.clearToken()
                        }
                    // save the token to local storage
                    local.saveToken(token.data!!.token)
                    remote.getUser(username).successful { user ->
                        if (user.isSuccessful()) {
                            local.saveUser(user.data!!)
                            ResultWrapper.Success(user.message)
                        } else ResultWrapper.Failure(user.message)
                    }
                    transactions.sync()
                }

                else -> ResultWrapper.Failure(token.message)
            }
        }
    }

    /**
     * Register this user and generate a new user id
     */
    suspend fun register(
        firstname: String,
        lastname: String,
        username: String,
        password: String,
    ): ResultWrapper<String> {
        return remote.register(
            CreateUserRequest(
                firstname = firstname,
                lastname = lastname,
                username = username,
                password = password,
            )
        ).successful { response ->
            if (response.isSuccessful()) {
                ResultWrapper.Success(response.message)
            } else ResultWrapper.Failure(response.message)
        }
    }

    /**
     * Clear the user from the device
     */
    suspend fun clear() = local.clear()
}
