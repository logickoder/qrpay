package dev.logickoder.qrpay.ui.shared.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.data.repository.TransactionsRepo
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.utils.Amount
import dev.logickoder.qrpay.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val user: UserRepo,
    private val repo: TransactionsRepo,
) : ViewModel() {

    var amount: Amount by mutableStateOf(0.0)
    var recipientsId by mutableStateOf("")
    var note by mutableStateOf("")

    var uiState: ResultWrapper<Unit>? by mutableStateOf(null)

    fun send(userId: String) = viewModelScope.launch {
        uiState = ResultWrapper.Loading
        withContext(Dispatchers.IO) {
            uiState = repo.sendMoney(amount, note, userId, recipientsId)
            // refresh the local store with the new results from the server
            user.login(userId)
            repo.fetchTransactions(userId)
        }
    }
}