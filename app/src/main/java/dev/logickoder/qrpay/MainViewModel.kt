package dev.logickoder.qrpay

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.app.data.model.Transaction
import dev.logickoder.qrpay.app.data.model.User
import dev.logickoder.qrpay.app.data.repository.TransactionsRepository
import dev.logickoder.qrpay.app.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    val transactions = mutableStateListOf<Transaction>()

    val user = mutableStateOf<User?>(null)

    val isRefreshing = mutableStateOf(false)

    init {
        viewModelScope.launch {
            launch {
                userRepository.currentUser.collect {
                    user.value = it
                }
            }
            launch {
                transactionsRepository.transactions.collect { data ->
                    transactions.clear()
                    transactions.addAll(data)
                }
            }
        }
    }

    fun refresh() {
        val userId = user.value?.id
        if (userId != null) viewModelScope.launch(Dispatchers.Default) {
            isRefreshing.value = true
            coroutineScope {
                launch {
                    userRepository.login(userId)
                }
                launch {
                    transactionsRepository.fetchTransactions(userId)
                }
            }
            isRefreshing.value = false
        }
    }

    fun logout() = viewModelScope.launch {
        userRepository.clear()
        transactionsRepository.clear()
    }
}
