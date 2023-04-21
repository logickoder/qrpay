package dev.logickoder.qrpay.sendmoney

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SendMoneyRoute(
    currency: String,
    modifier: Modifier = Modifier,
    viewModel: SendMoneyViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    SendMoneyScreen(
        modifier = modifier,
        currency = currency,
        state = state,
        onUpdate = viewModel::update,
        onSend = viewModel::send,
    )
}