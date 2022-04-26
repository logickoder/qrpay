package dev.logickoder.qrpay.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.component.*
import dev.logickoder.qrpay.ui.shared.viewmodel.DefaultCurrency
import dev.logickoder.qrpay.ui.shared.viewmodel.HomeModal
import dev.logickoder.qrpay.ui.shared.viewmodel.QrPayViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: QrPayViewModel = viewModel()
) = with(viewModel) {

    val coroutineScope = rememberCoroutineScope()
    val modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var modalScreen by remember { mutableStateOf(HomeModal.SendMoney) }

    BackHandler(modalState.currentValue != ModalBottomSheetValue.Hidden) {
        coroutineScope.launch {
            modalState.animateTo(
                when (modalState.currentValue) {
                    ModalBottomSheetValue.Expanded -> ModalBottomSheetValue.HalfExpanded
                    else -> ModalBottomSheetValue.Hidden
                }
            )
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalState,
        sheetContent = {
            when (modalScreen) {
                HomeModal.PaymentHistory -> PaymentHistory(
                    transactions = transactions,
                    currency = user?.currency ?: DefaultCurrency,
                )
                HomeModal.ReceiveMoney -> ReceiveMoney(
                    userId = user?.id ?: ""
                )
                HomeModal.SendMoney -> SendMoney(
                    userId = user?.id ?: "",
                    currency = user?.currency ?: DefaultCurrency
                )
            }
        }) {
        Scaffold(
            modifier = modifier,
            topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) }
        ) {
            if (user == null) LoginScreen()
            HomeContent(
                viewModel,
                modifier = Modifier
                    .fillMaxSize()
            ) { screen ->
                coroutineScope.launch {
                    modalScreen = screen
                    modalState.show()
                }
            }
        }
    }
}

@Composable
private fun HomeContent(
    viewModel: QrPayViewModel,
    modifier: Modifier = Modifier,
    showSheet: (HomeModal) -> Unit,
) = with(viewModel) {

    Column(modifier = modifier.verticalScroll(state = rememberScrollState())) {

        val padding = dimensionResource(id = R.dimen.secondary_padding)
        val verticalPadding = dimensionResource(id = R.dimen.primary_padding)

        val cardModifier = Modifier.padding(
            bottom = verticalPadding, start = padding, end = padding,
        )
        val cardContentModifier = Modifier.fillMaxWidth()

        Card(modifier = cardModifier.padding(top = verticalPadding / 2)) {
            BalanceSummaryCard(
                user?.balance ?: 0.0,
                user?.currency ?: "$",
                cardContentModifier
            )
        }
        Card(modifier = cardModifier) {
            InfoCard(
                title = R.string.your_userid,
                content = user?.id ?: "",
                caption = R.string.login_transactions,
                icon = Icons.Outlined.Contacts,
                modifier = cardContentModifier,
            )
        }
        Card(modifier = cardModifier) {
            InfoCard(
                title = R.string.transactions,
                content = transactions.size.toString(),
                caption = R.string.total_transactions,
                icon = Icons.Outlined.TrendingUp,
                modifier = cardContentModifier,
            )
        }
        Card(modifier = cardModifier) {
            DemoCard(
                userName = user?.name ?: "",
                demoBalance = user?.balance ?: 0.0,
                currency = user?.currency ?: "$",
                modifier = cardContentModifier
            )
        }
        Card(modifier = cardModifier.clickable { showSheet(HomeModal.SendMoney) }) {
            ActionCard(
                imageId = R.drawable.ic_initiate_money_transfer,
                textId = R.string.send_money,
                modifier = cardContentModifier,
            )
        }
        Card(modifier = cardModifier.clickable { showSheet(HomeModal.ReceiveMoney) }) {
            ActionCard(
                imageId = R.drawable.ic_request_money,
                textId = R.string.receive_money,
                modifier = cardContentModifier
            )
        }
        Card(modifier = cardModifier.clickable { showSheet(HomeModal.PaymentHistory) }) {
            ActionCard(
                imageId = R.drawable.ic_payment_history,
                textId = R.string.payment_history,
                modifier = cardContentModifier
            )
        }
        Footer(
            listOf(Currency.getAvailableCurrencies().firstOrNull { it.symbol == user?.currency }
                .toString()),
            {},
            {}
        )
    }
}

