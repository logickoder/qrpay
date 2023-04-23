package dev.logickoder.qrpay.receivemoney

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReceiveMoneyRoute(
    modifier: Modifier = Modifier,
    viewModel: ReceiveMoneyViewModel = viewModel(),
) {
    val username by viewModel.username.collectAsState()

    ReceiveMoneyScreen(
        modifier = modifier,
        username = username,
    )
}