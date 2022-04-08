package dev.logickoder.qrpay.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.data.models.Transaction
import dev.logickoder.qrpay.ui.shared.components.Action
import dev.logickoder.qrpay.ui.shared.components.Payment
import dev.logickoder.qrpay.ui.theme.Theme

@Composable
fun PaymentHistory(
    transactions: List<Transaction>,
    currency: String,
    modifier: Modifier = Modifier,
) = Action(
    modifier = modifier,
    title = R.string.payment_history,
    action = {
        Button(
            onClick = { },
            contentPadding = PaddingValues(4.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = null,
            )
            Text(
                text = stringResource(id = R.string.refresh),
                style = Theme.typography.caption,
            )
        }
    }
) {
    val padding = dimensionResource(id = R.dimen.primary_padding)
    Spacer(modifier = Modifier.height(padding / 2))
    LazyColumn(modifier = Modifier.background(Theme.colors.surface)) {
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
