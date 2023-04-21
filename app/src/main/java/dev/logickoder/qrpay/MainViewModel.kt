package dev.logickoder.qrpay

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.app.data.repository.TransactionsRepository
import dev.logickoder.qrpay.app.data.repository.UserRepository
import dev.logickoder.qrpay.app.data.sync.SyncLauncher
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val userRepository: UserRepository,
    private val syncLauncher: SyncLauncher,
) : ViewModel() {

    val transactions = transactionsRepository.transactions.map {
        it.toPersistentList()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), persistentListOf()
    )

    val user = userRepository.currentUser.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), null
    )

    var refreshing by mutableStateOf(false)

    fun refresh() {
        viewModelScope.launch {
            refreshing = true
            syncLauncher.sync()
            refreshing = false
        }
    }

    fun logout() = viewModelScope.launch {
        userRepository.clear()
        transactionsRepository.clear()
    }
}
