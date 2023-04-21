package dev.logickoder.qrpay.paymenthistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import dev.logickoder.qrpay.app.theme.secondaryPadding
import dev.logickoder.qrpay.app.theme.smallPadding
import dev.logickoder.qrpay.app.utils.formatted
import dev.logickoder.qrpay.app.utils.isDebit
import dev.logickoder.qrpay.app.utils.transaction
import dev.logickoder.qrpay.app.widgets.Icon
import dev.logickoder.qrpay.model.Transaction

@Composable
fun Payment(
    currency: String,
    transaction: Transaction,
    modifier: Modifier = Modifier,
) {
    val description = remember(transaction) {
        val (value, sender, recipient) = transaction.description
        val trailing = when {
            sender != null -> "From $sender"
            recipient != null -> "To $recipient"
            else -> null
        }
        "$value // $trailing"
    }

    Row(
        modifier = modifier,
        content = {
            Icon(icon = Icons.Outlined.AccountBalanceWallet)
            Spacer(modifier = Modifier.width(secondaryPadding()))
            Column(
                content = {
                    Text(
                        "${transaction.type.name} (${transaction.time.formatted})",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                    Text(
                        modifier = Modifier.padding(vertical = smallPadding()),
                        text = description,
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        transaction.amount.transaction(currency),
                        color = if (transaction.amount.isDebit) {
                            MaterialTheme.colorScheme.error
                        } else MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    )
                }
            )
        }
    )
}
