package dev.logickoder.qrpay.app.data.sync

import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.repository.UserRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class UserSync @Inject constructor(
    private val users: UserRepository
) : Sync {

    override suspend fun sync() {
        val id = users.currentUser.first()?.id ?: return
        when (val result = users.login(id)) {
            is ResultWrapper.Success -> Napier.d(
                "Refreshed User: ${result.data.id} from server"
            )

            is ResultWrapper.Failure -> Napier.e(
                "Failed to refresh user from server", result.error
            )

            ResultWrapper.Loading -> {
                // Do nothing
            }
        }
    }
}