package dev.logickoder.qrpay.sendmoney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.repository.TransactionsRepository
import dev.logickoder.qrpay.app.data.sync.SyncLauncher
import dev.logickoder.qrpay.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val repository: TransactionsRepository,
    private val syncLauncher: SyncLauncher,
) : ViewModel() {

    private val apiResponse = MutableStateFlow<Response<String>?>(null)
    private val loading = MutableStateFlow(false)

    private val _state = MutableStateFlow(SendMoneyState())
    val state = combine(
        flow = _state,
        flow2 = apiResponse,
        flow3 = loading,
        transform = { state, response, loading ->
            state.copy(
                apiResponse = response,
                loading = loading,
                enabled = state.amount > 0f && state.recipient.isNotBlank() && state.note.isNotBlank()
            )
        }
    ).flowOn(Dispatchers.Default).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SendMoneyState(),
    )

    fun update(state: SendMoneyState) {
        _state.update { state }
    }

    fun send() {
        viewModelScope.launch {
            loading.update { true }

            val response = _state.value.run {
                repository.sendMoney(
                    amount = amount,
                    narration = note,
                    recipient = recipient
                )
            }

            when (response) {
                is ResultWrapper.Failure -> apiResponse.update {
                    Response(false, response.error.localizedMessage.orEmpty(), null)
                }

                is ResultWrapper.Success -> apiResponse.update {
                    Response(true, response.data, null)
                }

                ResultWrapper.Loading -> {
                    // do nothing
                }
            }
            // refresh information from the server
            syncLauncher.sync()

            loading.update { false }
        }
    }
}
