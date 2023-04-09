package dev.logickoder.qrpay.payment_history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.data.model.Transaction
import dev.logickoder.qrpay.app.theme.Theme
import dev.logickoder.qrpay.app.theme.paddingSecondary
import dev.logickoder.qrpay.app.utils.formattedTransactionWith
import dev.logickoder.qrpay.app.utils.isDebit
import dev.logickoder.qrpay.app.widgets.Icon

@Composable
fun Payment(
    currency: String,
    transaction: Transaction,
    modifier: Modifier = Modifier,
) = with(transaction) {
    Row(
        modifier = modifier,
        content = {
            Icon(icon = Icons.Outlined.AccountBalanceWallet)
            Spacer(modifier = Modifier.width(paddingSecondary()))
            Column(
                content = {
                    Text(
                        "${type.uppercase()} ($date)",
                        style = Theme.typography.labelMedium,
                        color = Theme.colorScheme.onTertiary,
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.secondary_padding) / 2),
                        text = comment,
                        style = Theme.typography.labelMedium,
                    )
                    Text(
                        amount.formattedTransactionWith(currency),
                        color = if (amount.isDebit) Theme.colorScheme.error else Theme.colorScheme.secondary,
                        style = Theme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    )
                }
            )
        }
    )
}
