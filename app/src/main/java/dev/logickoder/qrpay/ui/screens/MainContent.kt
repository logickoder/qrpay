package dev.logickoder.qrpay.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.screens.home.HomeModal
import dev.logickoder.qrpay.ui.screens.home.HomeScreen
import dev.logickoder.qrpay.ui.screens.login.LoginScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) = with(viewModel) {

    val coroutineScope = rememberCoroutineScope()
    val modalState = rememberModalBottomSheetState()
    var modal by remember { mutableStateOf(HomeModal.SendMoney) }

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
                refresh = ::refresh,
                showSheet = { screen ->
                    coroutineScope.launch {
                        modal = screen
                        modalState.show()
                    }
                },
                logout = ::logout
            )
        }
    )

    ModalBottomSheet(
        sheetState = modalState,
        content = {
            HomeModal(
                modal = modal,
                modalState = modalState.currentValue,
                user = user,
                transactions = transactions,
                onStateChanged = { state ->
                    coroutineScope.launch {
                        when (state) {
                            SheetValue.Hidden -> modalState.hide()
                            SheetValue.Expanded -> modalState.expand()
                            SheetValue.PartiallyExpanded -> modalState.partialExpand()
                        }
                    }
                },
            )
        },
        onDismissRequest = {

        }
    )
}
