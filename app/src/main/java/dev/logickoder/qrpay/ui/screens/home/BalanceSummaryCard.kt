package dev.logickoder.qrpay.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.composables.Icon
import dev.logickoder.qrpay.ui.shared.modifiers.nonExistent
import dev.logickoder.qrpay.ui.theme.Theme
import dev.logickoder.qrpay.ui.theme.TintedText
import dev.logickoder.qrpay.utils.Amount
import dev.logickoder.qrpay.utils.formattedWith

@Composable
fun BalanceSummaryCard(
    balance: Amount?,
    currency: String?,
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
        style = Theme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium),
        color = Theme.colorScheme.secondaryContainer,
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
        style = Theme.typography.labelMedium,
        color = TintedText,
    )
    Text(
        modifier = Modifier
            .constrainAs(amount) {
                bottom.linkTo(icon.bottom)
                start.linkTo(caption.start)
            }
            .nonExistent(balance),
        text = balance?.formattedWith(currency.toString()).toString(),
        style = Theme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
    )
}
