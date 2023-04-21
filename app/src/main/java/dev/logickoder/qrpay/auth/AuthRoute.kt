package dev.logickoder.qrpay.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthRoute(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    AuthScreen(
        modifier = modifier,
        state = state,
        update = viewModel::update,
        onClick = viewModel::submit,
    )
}