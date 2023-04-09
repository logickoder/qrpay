package dev.logickoder.qrpay.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.QRPayTheme
import dev.logickoder.qrpay.app.theme.Theme
import dev.logickoder.qrpay.app.theme.paddingSecondary
import dev.logickoder.qrpay.app.utils.Amount
import dev.logickoder.qrpay.app.utils.formattedWith
import dev.logickoder.qrpay.app.widgets.Card
import dev.logickoder.qrpay.app.widgets.Icon
import dev.logickoder.qrpay.app.widgets.nonExistent

@Composable
fun BalanceSummaryCard(
    balance: Amount?,
    currency: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        content = {
            val padding = paddingSecondary()
            Text(
                text = stringResource(id = R.string.balance_summary),
                style = Theme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                color = Theme.colorScheme.secondaryContainer,
            )
            Spacer(modifier = Modifier.height(padding))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(icon = Icons.Outlined.AccountBalanceWallet)
                    Spacer(modifier = Modifier.width(padding))
                    Column(
                        content = {
                            Text(
                                text = stringResource(id = R.string.total_balance),
                                style = Theme.typography.labelMedium,
                                color = Theme.colorScheme.onTertiary,
                            )
                            Text(
                                modifier = Modifier.nonExistent(balance),
                                text = balance?.formattedWith(currency.toString()).toString(),
                                style = Theme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun BalanceSummaryPreview() = QRPayTheme {
    Card {
        BalanceSummaryCard(balance = 50000.0, currency = "$")
    }
}