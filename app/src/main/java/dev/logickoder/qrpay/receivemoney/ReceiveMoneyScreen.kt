package dev.logickoder.qrpay.receivemoney

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.primaryPadding
import dev.logickoder.qrpay.app.widgets.Action
import dev.logickoder.qrpay.app.widgets.QRCode

@Composable
fun ReceiveMoneyScreen(
    username: String,
    modifier: Modifier = Modifier,
) {
    Action(
        modifier = modifier,
        title = R.string.receive_money,
        content = {
            Column {
                QRCode(
                    qrCode = username,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(primaryPadding())
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = primaryPadding()),
                    text = stringResource(id = R.string.use_user_id, username),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    )
}