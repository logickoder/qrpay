package dev.logickoder.qrpay.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) = with(viewModel) {

    val coroutineScope = rememberCoroutineScope()
    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    var modal by remember { mutableStateOf(HomeModal.SendMoney) }

    ModalBottomSheetLayout(
        sheetState = modalState,
        sheetContent = {
            HomeModal(
                modal = modal,
                modalState = modalState.currentValue,
                user = user,
                transactions = transactions,
                onStateChanged = { state ->
                    coroutineScope.launch { modalState.animateTo(state) }
                },
            )
        },
        content = {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.app_name)
                            )
                        }
                    )
                },
                content = {
                    if (user == null) LoginScreen()
                    HomeScreen(
                        user = user,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        isRefreshing = isRefreshing,
                        refresh = { viewModel.refresh() },
                        showSheet = { screen ->
                            coroutineScope.launch {
                                modal = screen
                                modalState.show()
                            }
                        }
                    )
                }
            )
        }
    )
}
