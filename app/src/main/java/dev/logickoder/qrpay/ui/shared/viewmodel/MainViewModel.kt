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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val transactionsRepo: TransactionsRepo,
    private val userRepo: UserRepo,
) : AndroidViewModel(app) {

    val transactions = mutableStateListOf<Transaction>()

    var user by mutableStateOf<User?>(null)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                userRepo.currentUser.flowOn(Dispatchers.Main).collect {
                    user = it
                }
            }
            launch {
                transactionsRepo.transactions.flowOn(Dispatchers.Main).collect { data ->
                    transactions.removeAll { true }
                    transactions.addAll(data)
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

    fun logout() = viewModelScope.launch {
        isRefreshing = true
        userRepo.clear()
        transactionsRepo.clear()
        isRefreshing = false
    }
}
