package dev.logickoder.qrpay.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.data.model.Transaction
import dev.logickoder.qrpay.data.model.User
import dev.logickoder.qrpay.ui.screens.payment_history.PaymentHistory
import dev.logickoder.qrpay.ui.screens.receive_money.ReceiveMoney
import dev.logickoder.qrpay.ui.screens.send_money.SendMoney
import dev.logickoder.qrpay.ui.shared.composables.Card

enum class HomeModal {
    SendMoney,
    ReceiveMoney,
    PaymentHistory
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeModal(
    modal: HomeModal,
    modalState: ModalBottomSheetValue,
    user: User?,
    transactions: List<Transaction>,
    modifier: Modifier = Modifier,
    onStateChanged: (ModalBottomSheetValue) -> Unit,
) {
    BackHandler(
        enabled = modalState != ModalBottomSheetValue.Hidden
    ) {
        onStateChanged(
            when (modalState) {
                ModalBottomSheetValue.Expanded -> ModalBottomSheetValue.HalfExpanded
                else -> ModalBottomSheetValue.Hidden
            }
        )
    }

    val id = user?.id ?: ""
    val currency = user?.currency ?: ""

    when (modal) {
        HomeModal.PaymentHistory -> PaymentHistory(
            transactions = transactions,
            currency = currency,
            modifier = modifier,
        )
        HomeModal.ReceiveMoney -> ReceiveMoney(
            userId = id
        )
        HomeModal.SendMoney -> SendMoney(
            userId = id,
            currency = currency,
        )
    }
}

@Composable
fun HomeScreen(
    user: User?,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    refresh: () -> Unit,
    showSheet: (HomeModal) -> Unit,
    logout: () -> Unit,
) = SwipeRefresh(
    state = rememberSwipeRefreshState(isRefreshing),
    onRefresh = { refresh() }
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = user) {
        // scrolls to the top of the screen when the user is null
        // which is what usually happens when the user is logged out
        if (user == null) scrollState.animateScrollTo(
            value = 0,
            animationSpec = tween(durationMillis = 1_000, easing = LinearEasing),
        )
    }

    Column(
        modifier = modifier.verticalScroll(state = scrollState)
    ) {

        val padding = dimensionResource(id = R.dimen.secondary_padding)
        val verticalPadding = dimensionResource(id = R.dimen.primary_padding)

        val cardModifier = Modifier.padding(
            bottom = verticalPadding, start = padding, end = padding,
        )
        val cardContentModifier = Modifier.fillMaxWidth()

        Card(modifier = cardModifier.padding(top = verticalPadding / 2)) {
            BalanceSummaryCard(
                user?.balance,
                user?.currency,
                cardContentModifier
            )
        }
        Card(modifier = cardModifier) {
            InfoCard(
                title = R.string.your_userid,
                content = user?.id,
                caption = R.string.login_transactions,
                icon = Icons.Outlined.Contacts,
                modifier = cardContentModifier,
            )
        }
        Card(modifier = cardModifier) {
            InfoCard(
                title = R.string.transactions,
                content = user?.transactions,
                caption = R.string.total_transactions,
                icon = Icons.Outlined.TrendingUp,
                modifier = cardContentModifier,
            )
        }
        Card(modifier = cardModifier) {
            DemoCard(
                userName = user?.name,
                demoBalance = user?.demoBalance,
                currency = user?.currency,
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
            currencies = listOf(user?.currency ?: ""),
            onCurrencyChange = {},
            logout = logout,
        )
    }
}

