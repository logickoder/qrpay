package dev.logickoder.qrpay.receive_money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.data.model.QrCode
import dev.logickoder.qrpay.app.theme.Theme
import dev.logickoder.qrpay.app.widgets.Action
import dev.logickoder.qrpay.app.widgets.QRCode

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
            style = Theme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}