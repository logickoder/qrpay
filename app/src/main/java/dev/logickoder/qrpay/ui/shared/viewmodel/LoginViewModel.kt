package dev.logickoder.qrpay.ui.shared.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.utils.ResultWrapper
import dev.logickoder.qrpay.utils.createWork
import dev.logickoder.qrpay.workers.TransactionWorker
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoginScreenState {
    Register,
    Login
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val app: Application
) : AndroidViewModel(app) {
    var loginScreenState by mutableStateOf(LoginScreenState.Login)
    var working by mutableStateOf(false)

    var userId by mutableStateOf("")
    var name by mutableStateOf("")
    var error by mutableStateOf("")

    fun login() = viewModelScope.launch {
        error = ""
        working = true
        val result = (if (loginScreenState == LoginScreenState.Login)
            userRepo.login(userId)
        else
            userRepo.register(name))
        if (result is ResultWrapper.Failure) error = result.error.message.toString()
        working = false
        // run the transaction worker
        app.createWork<TransactionWorker>()
    }
}
