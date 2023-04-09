package dev.logickoder.qrpay.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.data.model.User
import dev.logickoder.qrpay.app.theme.QRPayTheme
import dev.logickoder.qrpay.app.theme.paddingPrimary
import dev.logickoder.qrpay.app.theme.paddingSecondary
import dev.logickoder.qrpay.app.widgets.Card
import dev.logickoder.qrpay.app.widgets.pullrefresh.PullRefreshIndicator
import dev.logickoder.qrpay.app.widgets.pullrefresh.pullRefresh
import dev.logickoder.qrpay.app.widgets.pullrefresh.rememberPullRefreshState

@Composable
fun HomeScreen(
    user: State<User?>,
    isRefreshing: State<Boolean>,
    modifier: Modifier = Modifier,
    refresh: () -> Unit,
    showSheet: (HomeModal) -> Unit,
    logout: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing.value, refresh)
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = user, block = {
        // scrolls to the top of the screen when the user is null
        // which is what usually happens when the user is logged out
        if (user.value == null) scrollState.animateScrollTo(
            value = 0,
            animationSpec = tween(durationMillis = 1_000, easing = LinearEasing),
        )
    })

    Box(
        modifier = modifier.pullRefresh(pullRefreshState),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState),
                content = {

                    val cardModifier = Modifier.padding(
                        bottom = paddingPrimary(),
                        start = paddingSecondary(),
                        end = paddingSecondary(),
                    )
                    val cardContentModifier = Modifier.fillMaxWidth()

                    Spacer(modifier = Modifier.height(paddingPrimary() / 2))
                    Card(
                        modifier = cardModifier,
                        content = {
                            BalanceSummaryCard(
                                user.value?.balance,
                                user.value?.currency,
                                cardContentModifier
                            )
                        }
                    )
                    Card(
                        modifier = cardModifier,
                        content = {
                            InfoCard(
                                title = R.string.your_userid,
                                content = user.value?.id,
                                caption = R.string.login_transactions,
                                icon = Icons.Outlined.Contacts,
                                modifier = cardContentModifier,
                            )
                        }
                    )
                    Card(
                        modifier = cardModifier,
                        content = {
                            InfoCard(
                                title = R.string.transactions,
                                content = user.value?.transactions,
                                caption = R.string.total_transactions,
                                icon = Icons.Outlined.TrendingUp,
                                modifier = cardContentModifier,
                            )
                        }
                    )
                    Card(
                        modifier = cardModifier,
                        content = {
                            DemoCard(
                                userName = user.value?.name,
                                demoBalance = user.value?.demoBalance,
                                currency = user.value?.currency,
                                modifier = cardContentModifier
                            )
                        }
                    )
                    Card(
                        modifier = cardModifier.clickable { showSheet(HomeModal.SendMoney) },
                        content = {
                            ActionCard(
                                imageId = R.drawable.ic_initiate_money_transfer,
                                textId = R.string.send_money,
                                modifier = cardContentModifier,
                            )
                        }
                    )
                    Card(
                        modifier = cardModifier.clickable { showSheet(HomeModal.ReceiveMoney) },
                        content = {
                            ActionCard(
                                imageId = R.drawable.ic_request_money,
                                textId = R.string.receive_money,
                                modifier = cardContentModifier
                            )
                        }
                    )
                    Card(
                        modifier = cardModifier.clickable { showSheet(HomeModal.PaymentHistory) },
                        content = {
                            ActionCard(
                                imageId = R.drawable.ic_payment_history,
                                textId = R.string.payment_history,
                                modifier = cardContentModifier
                            )
                        }
                    )
                    Footer(
                        currencies = listOf(user.value?.currency.orEmpty()),
                        onCurrencyChange = {},
                        logout = logout,
                    )
                }
            )
            PullRefreshIndicator(
                isRefreshing.value,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() = QRPayTheme {
    HomeScreen(
        user = remember {
            mutableStateOf(
                User(
                    name = "logickoder",
                    id = "",
                    balance = 40000.0,
                    transactions = 1,
                    currency = "$"
                )
            )
        },
        isRefreshing = remember {
            mutableStateOf(false)
        },
        refresh = {},
        showSheet = {},
        logout = {},
    )
}