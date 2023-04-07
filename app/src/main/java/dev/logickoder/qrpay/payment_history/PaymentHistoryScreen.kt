package dev.logickoder.qrpay.payment_history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.data.model.Transaction
import dev.logickoder.qrpay.app.widgets.Action

@Composable
fun PaymentHistory(
    transactions: List<Transaction>,
    currency: String,
    modifier: Modifier = Modifier,
) = Action(
    modifier = modifier,
    title = R.string.payment_history,
) {
    val padding = dimensionResource(id = R.dimen.primary_padding)
    Spacer(modifier = Modifier.height(padding / 2))
    LazyColumn {
        items(transactions) { transaction ->
            Payment(
                currency = currency,
                transaction = transaction,
                modifier = Modifier.padding(vertical = padding / 2, horizontal = padding)
            )
        }
    }
    Spacer(modifier = Modifier.height(padding / 2))
}
