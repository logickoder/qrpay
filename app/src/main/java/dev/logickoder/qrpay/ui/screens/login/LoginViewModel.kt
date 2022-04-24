package dev.logickoder.qrpay.ui.screens.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.utils.ResultWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoginScreenState {
    Register,
    Login
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    app: Application
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
    }
}
