package dev.logickoder.qrpay.ui.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.shared.components.Action

@Composable
fun ReceiveMoney(
    userId: String,
    modifier: Modifier = Modifier,
) = Action(
    modifier = modifier,
    title = R.string.receive_money,
) {
    val padding = dimensionResource(id = R.dimen.primary_padding)
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding, vertical = padding * 2),
        text = stringResource(id = R.string.use_user_id, userId),
        textAlign = TextAlign.Center,
    )
}
