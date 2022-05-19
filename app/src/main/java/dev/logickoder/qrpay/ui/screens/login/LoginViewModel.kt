package dev.logickoder.qrpay.ui.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.data.repository.TransactionsRepo
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.utils.ResultWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoginScreenState(
    private var _value: MutableState<String> = mutableStateOf("")
) {
    Login,
    Register,
    Error;

    var value: String
        get() = _value.value
        set(value) {
            _value.value = value
        }
}

val LoginScreenState.isError: Boolean
    get() = this == LoginScreenState.Error

val LoginScreenState.isLogin: Boolean
    get() = this == LoginScreenState.Login

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val transactionsRepo: TransactionsRepo,
) : ViewModel() {
    private var lastScreen = LoginScreenState.Login

    var uiState by mutableStateOf(lastScreen)
        private set

    var working by mutableStateOf(false)
        private set

    fun switchScreen(state: LoginScreenState) {
        lastScreen = if (state != LoginScreenState.Error) state else lastScreen
        uiState = state
    }

    fun buttonClick() = viewModelScope.launch {
        working = true
        when (uiState) {
            LoginScreenState.Login -> userRepo.login(uiState.value).also {
                // fetch the new batch of transactions
                if (it is ResultWrapper.Success)
                    transactionsRepo.fetchTransactions(it.data.id)
            }
            LoginScreenState.Register -> userRepo.register(uiState.value)
            LoginScreenState.Error -> {
                switchScreen(lastScreen)
                ResultWrapper.Loading
            }
        }.let { result ->
            if (result is ResultWrapper.Failure) {
                switchScreen(LoginScreenState.Error)
                uiState.value = result.error.message ?: ""
            }
        }
        working = false
    }
}
