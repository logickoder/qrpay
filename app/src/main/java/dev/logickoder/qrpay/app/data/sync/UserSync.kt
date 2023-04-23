package dev.logickoder.qrpay.app.data.sync

import dev.logickoder.qrpay.app.data.local.UserStore
import dev.logickoder.qrpay.app.data.remote.QrPayApi
import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.model.isSuccessful
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserSync @Inject constructor(
    private val local: UserStore,
    private val remote: QrPayApi,
) : Sync {

    override suspend fun sync(): ResultWrapper<String> {
        val username = local.getUser().first()?.username
            ?: return ResultWrapper.Failure("No user saved locally")

        return when (val result = remote.getUser(username)) {
            is ResultWrapper.Success -> {
                if (result.data.isSuccessful()) {
                    local.saveUser(result.data.data!!)
                    Napier.d("Successfully refreshed user from server")
                    ResultWrapper.Success(result.data.message)
                } else ResultWrapper.Failure(result.data.message)
            }

            is ResultWrapper.Failure -> {
                Napier.e("Failed to refresh user from server", result.error)
                ResultWrapper.Failure(result.error)
            }

            ResultWrapper.Loading -> ResultWrapper.Loading
        }
    }
}