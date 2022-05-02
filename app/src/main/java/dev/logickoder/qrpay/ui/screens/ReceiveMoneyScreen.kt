package dev.logickoder.qrpay.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.data.model.QrCode
import dev.logickoder.qrpay.ui.shared.component.Action
import dev.logickoder.qrpay.ui.shared.component.QRCode
import dev.logickoder.qrpay.ui.theme.Theme

@Composable
fun ReceiveMoney(
    userId: String,
    modifier: Modifier = Modifier,
) = Action(
    modifier = modifier,
    title = R.string.receive_money,
) {
    Column {
        val padding = dimensionResource(id = R.dimen.primary_padding)
        QRCode(
            qrCode = QrCode(userId),
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding),
            text = stringResource(id = R.string.use_user_id, userId),
            style = Theme.typography.body2,
            textAlign = TextAlign.Center,
        )
    }
}
