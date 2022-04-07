package dev.logickoder.qrpay.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.components.Card
import dev.logickoder.qrpay.ui.shared.components.Icon
import dev.logickoder.qrpay.ui.theme.Theme
import dev.logickoder.qrpay.ui.theme.text
import dev.logickoder.qrpay.utils.formattedWith
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) = with(viewModel) {

    val horizontalPadding = dimensionResource(id = R.dimen.secondary_padding)

    val coroutineScope = rememberCoroutineScope()
    val modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = modalState,
        sheetContent = {
            Text("Hello")
        }) {
        Scaffold(
            modifier = modifier,
            topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) }
        ) {
            HomeContent(
                viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding)
            ) {
                coroutineScope.launch { modalState.show() }
            }
        }
    }
}

@Composable
private fun HomeContent(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    showSheet: () -> Unit,
) = with(viewModel) {

    Column(modifier = modifier.verticalScroll(state = rememberScrollState())) {

        val verticalPadding = dimensionResource(id = R.dimen.primary_padding)

        val cardModifier = Modifier.padding(bottom = verticalPadding)
        val cardContentModifier = Modifier.fillMaxWidth()

        Card(modifier = cardModifier.padding(top = verticalPadding / 2)) {
            BalanceSummaryCard(balance, currency, cardContentModifier)
        }
        Card(modifier = cardModifier) {
            InfoCard(
                title = R.string.your_userid,
                content = user.id,
                caption = R.string.login_transactions,
                icon = Icons.Outlined.Contacts,
                modifier = cardContentModifier,
            )
        }
        Card(modifier = cardModifier) {
            InfoCard(
                title = R.string.transactions,
                content = transactions.toString(),
                caption = R.string.total_transactions,
                icon = Icons.Outlined.TrendingUp,
                modifier = cardContentModifier,
            )
        }
        Card(modifier = cardModifier) {
            DemoCard(user.name, balance, currency, cardContentModifier)
        }
        Card(modifier = cardModifier.clickable { showSheet() }) {
            ActionCard(
                R.drawable.ic_initiate_money_transfer,
                R.string.send_money,
                cardContentModifier
            )
        }
        Card(modifier = cardModifier.clickable { showSheet() }) {
            ActionCard(
                R.drawable.ic_request_money,
                R.string.receive_money,
                cardContentModifier
            )
        }
        Card(modifier = cardModifier.clickable { showSheet() }) {
            ActionCard(
                R.drawable.ic_payment_history,
                R.string.payment_history,
                cardContentModifier
            )
        }
    }
}

@Composable
fun BalanceSummaryCard(
    balance: Double,
    currency: String,
    modifier: Modifier = Modifier
) = ConstraintLayout(modifier = modifier) {

    val padding = dimensionResource(id = R.dimen.secondary_padding)
    val (title, icon, caption, amount) = createRefs()

    Text(
        modifier = Modifier.constrainAs(title) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        },
        text = stringResource(id = R.string.balance_summary),
        style = Theme.typography.h5.copy(fontWeight = FontWeight.Medium),
        color = Theme.colors.text.title,
    )
    Icon(
        modifier = Modifier.constrainAs(icon) {
            top.linkTo(title.bottom, padding)
            start.linkTo(parent.start)
        },
        icon = Icons.Outlined.AccountBalanceWallet,
    )
    Text(
        modifier = Modifier.constrainAs(caption) {
            top.linkTo(icon.top)
            start.linkTo(icon.end, padding)
        },
        text = stringResource(id = R.string.total_balance),
        style = Theme.typography.caption,
        color = Theme.colors.text.disabled,
    )
    Text(
        modifier = Modifier.constrainAs(amount) {
            bottom.linkTo(icon.bottom)
            start.linkTo(caption.start)
        },
        text = balance.formattedWith(currency),
        style = Theme.typography.h6.copy(fontWeight = FontWeight.Medium),
    )
}

@Composable
fun InfoCard(
    @StringRes title: Int,
    content: String,
    @StringRes caption: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) = ConstraintLayout(modifier = modifier) {

    val padding = dimensionResource(id = R.dimen.secondary_padding) / 2
    val (titleView, iconView, contentView, captionView) = createRefs()

    Text(
        modifier = Modifier.constrainAs(titleView) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        },
        text = stringResource(id = title),
        style = Theme.typography.body2,
    )
    Icon(
        modifier = Modifier.constrainAs(iconView) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        },
        icon = icon,
    )
    Text(
        modifier = Modifier.constrainAs(contentView) {
            top.linkTo(iconView.bottom)
            start.linkTo(parent.start)
        },
        text = content,
        style = Theme.typography.h5.copy(fontWeight = FontWeight.Medium),
        color = Theme.colors.text.title,
    )
    Text(
        modifier = Modifier.constrainAs(captionView) {
            top.linkTo(contentView.bottom, padding)
            start.linkTo(parent.start)
        },
        text = stringResource(id = caption),
        style = Theme.typography.caption,
    )
}

@Composable
fun DemoCard(
    userName: String,
    demoBalance: Double,
    currency: String,
    modifier: Modifier = Modifier,
) = ConstraintLayout(modifier = modifier) {

    val verticalPadding = dimensionResource(id = R.dimen.secondary_padding)
    val horizontalPadding = dimensionResource(id = R.dimen.primary_padding)

    val (left, right) = createRefs()

    Column(modifier = Modifier.constrainAs(left) {
        top.linkTo(parent.top)
        linkTo(start = parent.start, end = right.start, endMargin = horizontalPadding / 2)
        width = Dimension.fillToConstraints
    }) {
        Text(
            text = stringResource(id = R.string.demo_title, userName),
            style = Theme.typography.h6.copy(fontWeight = FontWeight.Medium),
            color = Theme.colors.text.title,
        )
        Text(
            text = stringResource(id = R.string.demo_subtitle),
            style = Theme.typography.caption,
        )
        Text(
            modifier = Modifier.padding(vertical = verticalPadding / 2),
            text = demoBalance.formattedWith(currency),
            style = Theme.typography.h5.copy(fontWeight = FontWeight.Medium),
            color = Theme.colors.primary,
        )
        Button(shape = Theme.shapes.medium, onClick = {}) {
            Text(text = stringResource(id = R.string.view_history))
        }
    }

    Image(
        modifier = Modifier
            .offset(y = horizontalPadding - 2.dp)
            .constrainAs(right) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
        painter = painterResource(id = R.drawable.prize_light),
        contentDescription = null,
    )
}

@Composable
fun ActionCard(
    @DrawableRes imageId: Int,
    @StringRes textId: Int,
    modifier: Modifier = Modifier,
) = Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
    Image(
        modifier = Modifier.size(dimensionResource(id = R.dimen.action_image_size)),
        painter = painterResource(id = imageId),
        contentDescription = null,
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.secondary_padding)))
    Text(
        text = stringResource(id = textId),
        style = Theme.typography.body2.copy(fontWeight = FontWeight.Bold),
    )
}
