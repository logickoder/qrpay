package dev.logickoder.qrpay.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.QRPayTheme
import dev.logickoder.qrpay.app.theme.secondaryPadding
import dev.logickoder.qrpay.app.utils.formatted
import dev.logickoder.qrpay.app.widgets.Card
import dev.logickoder.qrpay.app.widgets.Icon
import dev.logickoder.qrpay.app.widgets.nonExistent

@Composable
fun BalanceSummaryCard(
    balance: Float?,
    currency: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(id = R.string.balance_summary),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.secondaryContainer,
            )
            Spacer(modifier = Modifier.height(secondaryPadding()))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(icon = Icons.Outlined.AccountBalanceWallet)
                    Spacer(modifier = Modifier.width(secondaryPadding()))
                    Column(
                        content = {
                            Text(
                                text = stringResource(id = R.string.total_balance),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onTertiary,
                            )
                            Text(
                                modifier = Modifier.nonExistent(balance),
                                text = balance?.formatted(currency).orEmpty(),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
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
        BalanceSummaryCard(balance = 50000f, currency = "$")
    }
}