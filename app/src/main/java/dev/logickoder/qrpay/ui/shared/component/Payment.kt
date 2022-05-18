package dev.logickoder.qrpay.ui.shared.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.data.model.Transaction
import dev.logickoder.qrpay.ui.theme.Theme
import dev.logickoder.qrpay.ui.theme.TintedText
import dev.logickoder.qrpay.utils.formattedTransactionWith
import dev.logickoder.qrpay.utils.isDebit

@Composable
fun Payment(
    currency: String,
    transaction: Transaction,
    modifier: Modifier = Modifier,
) = Row(modifier = modifier) {

    Icon(icon = Icons.Outlined.AccountBalanceWallet)
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.secondary_padding)))
    Column {
        with(transaction) {
            Text(
                "${type.uppercase()} ($date)",
                style = Theme.typography.caption,
                color = TintedText,
            )
        }
        with(transaction) {
            Text(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.secondary_padding) / 2),
                text = comment,
                style = Theme.typography.caption,
            )
        }
        with(transaction) {
            Text(
                amount.formattedTransactionWith(currency),
                color = if (amount.isDebit) Theme.colors.error else Theme.colors.secondary,
                style = Theme.typography.body2.copy(fontWeight = FontWeight.Medium),
            )
        }
    }
}
