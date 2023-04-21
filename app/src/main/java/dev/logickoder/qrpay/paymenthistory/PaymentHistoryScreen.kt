package dev.logickoder.qrpay.paymenthistory

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.mediumPadding
import dev.logickoder.qrpay.app.theme.primaryPadding
import dev.logickoder.qrpay.app.widgets.Action
import dev.logickoder.qrpay.model.Transaction
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PaymentHistory(
    transactions: ImmutableList<Transaction>,
    currency: String,
    modifier: Modifier = Modifier,
) {
    val paymentModifier = Modifier.padding(
        vertical = mediumPadding(),
        horizontal = primaryPadding()
    )
    Action(
        modifier = modifier,
        title = R.string.payment_history,
        content = {
            Spacer(modifier = Modifier.height(mediumPadding()))
            LazyColumn {
                items(transactions) { transaction ->
                    Payment(
                        currency = currency,
                        transaction = transaction,
                        modifier = paymentModifier,
                    )
                }
            }
            Spacer(modifier = Modifier.height(mediumPadding()))
        }
    )
}