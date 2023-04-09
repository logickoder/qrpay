package dev.logickoder.qrpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.logickoder.qrpay.app.theme.QRPayTheme
import dev.logickoder.qrpay.app.theme.Theme
import dev.logickoder.qrpay.home.HomeModal
import dev.logickoder.qrpay.home.HomeScreen
import dev.logickoder.qrpay.login.LoginScreen
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRPayTheme {
                Content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetState by remember {
        derivedStateOf {
            scaffoldState.bottomSheetState
        }
    }
    val sheetValue by remember {
        derivedStateOf {
            scaffoldState.bottomSheetState.currentValue
        }
    }
    var modal by remember {
        mutableStateOf(HomeModal.SendMoney)
    }

    val onBack: () -> Unit = remember {
        {
            coroutineScope.launch {
                when (sheetValue) {
                    SheetValue.Expanded -> bottomSheetState.partialExpand()
                    else -> bottomSheetState.hide()
                }
            }
        }
    }
    val showSheet: (HomeModal) -> Unit = remember {
        {
            coroutineScope.launch {
                modal = it
                bottomSheetState.expand()
            }
        }
    }

    BackHandler(
        enabled = sheetValue != SheetValue.Hidden,
        onBack = onBack,
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = Theme.shapes.large,
        sheetContainerColor = Theme.colorScheme.background,
        containerColor = Theme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            )
        },
        content = {
            if (viewModel.user.value == null) LoginScreen()
            HomeScreen(
                user = viewModel.user,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                isRefreshing = viewModel.isRefreshing,
                refresh = viewModel::refresh,
                showSheet = showSheet,
                logout = viewModel::logout
            )
        },
        sheetContent = {
            HomeModal(
                modal = modal,
                user = viewModel.user,
                transactions = viewModel.transactions,
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QRPayTheme {
        Content()
    }
}
