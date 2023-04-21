package dev.logickoder.qrpay.receivemoney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.app.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReceiveMoneyViewModel @Inject constructor(
    repository: UserRepository,
) : ViewModel() {
    val username = repository.currentUser.map {
        it?.username.orEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "")
}