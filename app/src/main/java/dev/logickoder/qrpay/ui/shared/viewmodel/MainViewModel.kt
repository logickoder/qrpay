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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val transactionsRepo: TransactionsRepo,
    private val userRepo: UserRepo,
) : AndroidViewModel(app) {

    var user by mutableStateOf<User?>(null)
    val transactions = mutableStateListOf<Transaction>()

    var isRefreshing by mutableStateOf(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                userRepo.currentUser.collect {
                    it ?: return@collect
                    withContext(Dispatchers.Main) {
                        user = it
                    }
                }
            }
            launch {
                transactionsRepo.transactions.collect { data ->
                    withContext(Dispatchers.Main) {
                        transactions.removeAll { true }
                        transactions.addAll(data)
                    }
                }
            }
        }
    }

    fun refresh() = viewModelScope.launch {
        isRefreshing = true
        user?.run {
            coroutineScope {
                launch(Dispatchers.IO) {
                    userRepo.login(id)
                }
                launch(Dispatchers.IO) {
                    transactionsRepo.fetchTransactions(id)
                }
            }
        }
        isRefreshing = false
    }
}
