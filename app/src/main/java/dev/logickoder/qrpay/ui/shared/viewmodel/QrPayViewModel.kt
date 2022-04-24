package dev.logickoder.qrpay.ui.shared.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.data.model.Transaction
import dev.logickoder.qrpay.data.model.User
import dev.logickoder.qrpay.data.repository.TransactionsRepo
import dev.logickoder.qrpay.data.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class HomeModal {
    SendMoney,
    ReceiveMoney,
    PaymentHistory
}

const val DefaultCurrency = "$"

@HiltViewModel
class QrPayViewModel @Inject constructor(
    app: Application,
    private val transactionsRepo: TransactionsRepo,
    private val userRepo: UserRepo,
) : AndroidViewModel(app) {

    var user by mutableStateOf<User?>(null)

    var sendAmount by mutableStateOf(0.0)

    var recipientsId by mutableStateOf("")

    var note by mutableStateOf("")

    var transactions = mutableStateListOf<Transaction>()
        private set

    private fun getUserData() = viewModelScope.launch(Dispatchers.IO) {
        launch {
            userRepo.currentUser.collect {
                it ?: return@collect
                withContext(Dispatchers.Main) {
                    user = it
                }
            }
        }
        launch {
            transactionsRepo.transactions.collect {
                withContext(Dispatchers.Main) {
                    transactions.addAll(it.filter { data -> data !in transactions })
                }
            }
        }
    }

    init {
        getUserData()
    }
}
