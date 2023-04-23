package dev.logickoder.qrpay.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthRoute(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val onClick = remember {
        {
            viewModel.submit(context)
        }
    }

    AuthScreen(
        modifier = modifier,
        state = state,
        update = viewModel::update,
        onClick = onClick,
    )
}