package dev.logickoder.qrpay.data.repository

import dev.logickoder.qrpay.data.api.QrPayApi
import dev.logickoder.qrpay.data.model.User
import dev.logickoder.qrpay.data.store.DataStoreManager
import dev.logickoder.qrpay.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

/**
 * Handles operations relating to the user
 */
abstract class UserRepo(
    protected val local: DataStoreManager<User>,
    remote: QrPayApi
) : Repository(remote) {
    /**
     * The current logged in user
     */
    abstract val currentUser: Flow<User?>

    /**
     * Login with this [qrPayUid]
     */
    abstract suspend fun login(qrPayUid: String): ResultWrapper<User>

    /**
     * Register this user and generate a new user id
     */
    abstract suspend fun register(name: String): ResultWrapper<User>
}
