package dev.logickoder.qrpay.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.data.model.Transaction
import dev.logickoder.qrpay.data.model.User
import dev.logickoder.qrpay.data.repository.TransactionsRepository
import dev.logickoder.qrpay.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    val transactions = mutableStateListOf<Transaction>()

    var user by mutableStateOf<User?>(null)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                userRepository.currentUser.flowOn(Dispatchers.Main).collect {
                    user = it
                }
            }
            launch {
                transactionsRepository.transactions.flowOn(Dispatchers.Main).collect { data ->
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
                    userRepository.login(id)
                }
                launch(Dispatchers.IO) {
                    transactionsRepository.fetchTransactions(id)
                }
            }
        }
        isRefreshing = false
    }

    fun logout() = viewModelScope.launch {
        userRepository.clear()
        transactionsRepository.clear()
    }
}
