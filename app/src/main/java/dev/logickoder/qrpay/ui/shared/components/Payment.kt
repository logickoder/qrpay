package dev.logickoder.qrpay.ui.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.data.models.Transaction
import dev.logickoder.qrpay.ui.theme.Theme
import dev.logickoder.qrpay.ui.theme.text
import dev.logickoder.qrpay.utils.formattedTransactionWith
import dev.logickoder.qrpay.utils.isDebit
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun Payment(
    currency: String,
    transaction: Transaction,
    modifier: Modifier = Modifier,
) = Row(modifier = modifier) {

    Icon(icon = Icons.Outlined.AccountBalanceWallet)
    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.secondary_padding)))
    Column {
        Text(
            stringResource(
                id = R.string.transfer_date,
                transaction.date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            ),
            style = Theme.typography.caption,
            color = Theme.colors.text.disabled,
        )
        with(transaction) {
            Text(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.secondary_padding) / 2),
                text = stringResource(id = R.string.transfer_info, from.id, to.id, comment),
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
