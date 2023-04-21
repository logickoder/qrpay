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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.QRPayTheme
import dev.logickoder.qrpay.app.theme.primaryPadding
import dev.logickoder.qrpay.app.theme.secondaryPadding
import dev.logickoder.qrpay.app.widgets.Card
import dev.logickoder.qrpay.app.widgets.pullrefresh.PullRefreshIndicator
import dev.logickoder.qrpay.app.widgets.pullrefresh.pullRefresh
import dev.logickoder.qrpay.app.widgets.pullrefresh.rememberPullRefreshState
import dev.logickoder.qrpay.model.User

@Composable
fun HomeScreen(
    user: User?,
    currency: String,
    transactions: Int,
    refreshing: Boolean,
    modifier: Modifier = Modifier,
    refresh: () -> Unit,
    showSheet: (HomeModal) -> Unit,
    logout: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(refreshing, refresh)
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = user, block = {
        // scrolls to the top of the screen when the user is null
        // which is what usually happens when the user is logged out
        if (user == null) scrollState.animateScrollTo(
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
                        bottom = primaryPadding(),
                        start = secondaryPadding(),
                        end = secondaryPadding(),
                    )
                    val cardContentModifier = Modifier.fillMaxWidth()

                    Spacer(modifier = Modifier.height(primaryPadding() / 2))
                    Card(
                        modifier = cardModifier,
                        content = {
                            BalanceSummaryCard(
                                user?.balance,
                                currency,
                                cardContentModifier
                            )
                        }
                    )
                    Card(
                        modifier = cardModifier,
                        content = {
                            InfoCard(
                                title = R.string.your_username,
                                content = user?.username,
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
                                content = transactions,
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
                                userName = user?.username,
                                demoBalance = 50_000f,
                                currency = currency,
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
                        currencies = listOf('\u20a6'.toString()),
                        onCurrencyChange = {},
                        logout = logout,
                    )
                }
            )
            PullRefreshIndicator(
                refreshing,
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
        user = User(
            username = "logickoder",
            id = "",
            balance = 40000f,
        ),
        transactions = 10,
        currency = '\u20a6'.toString(),
        refreshing = false,
        refresh = {},
        showSheet = {},
        logout = {},
    )
}