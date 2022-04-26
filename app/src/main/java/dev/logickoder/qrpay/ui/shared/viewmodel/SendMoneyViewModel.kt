package dev.logickoder.qrpay.ui.shared.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.data.repository.TransactionsRepo
import dev.logickoder.qrpay.data.repository.UserRepo
import dev.logickoder.qrpay.utils.Amount
import dev.logickoder.qrpay.utils.ResultWrapper
import dev.logickoder.qrpay.utils.createWork
import dev.logickoder.qrpay.workers.TransactionWorker
import dev.logickoder.qrpay.workers.UserWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val user: UserRepo,
    private val repo: TransactionsRepo,
    private val app: Application,
) : AndroidViewModel(app) {

    var amount: Amount by mutableStateOf(0.0)
    var recipientsId by mutableStateOf("")
    var note by mutableStateOf("")

    var uiState: ResultWrapper<Unit>? by mutableStateOf(null)

    fun send(userId: String) = viewModelScope.launch {
        uiState = ResultWrapper.Loading
        withContext(Dispatchers.IO) {
            uiState = repo.sendMoney(amount, note, userId, recipientsId)
            // refresh the local store with the new results from the server
            app.createWork<UserWorker>()
            app.createWork<TransactionWorker>()
        }
    }
}
