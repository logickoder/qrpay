package dev.logickoder.qrpay.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.theme.QRPayTheme
import dev.logickoder.qrpay.app.theme.primaryPadding
import dev.logickoder.qrpay.app.theme.smallPadding
import dev.logickoder.qrpay.app.utils.formatted
import dev.logickoder.qrpay.app.widgets.Card
import dev.logickoder.qrpay.app.widgets.nonExistent


@Composable
fun DemoCard(
    userName: String?,
    demoBalance: Float,
    currency: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        content = {
            Column(
                modifier = Modifier.weight(1f),
                content = {
                    Text(
                        modifier = Modifier.nonExistent(userName),
                        text = stringResource(id = R.string.demo_title, userName.toString()),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                    )
                    Text(
                        text = stringResource(id = R.string.demo_subtitle),
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = smallPadding())
                            .nonExistent(demoBalance),
                        text = demoBalance.formatted(currency),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Button(
                        shape = MaterialTheme.shapes.medium,
                        onClick = {},
                        content = {
                            Text(text = stringResource(id = R.string.view_history))
                        }
                    )
                }
            )
            Image(
                modifier = Modifier
                    .absoluteOffset(y = primaryPadding())
                    .fillMaxWidth(.25f),
                painter = painterResource(id = R.drawable.prize_light),
                contentDescription = null,
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun DemoCardPreview() = QRPayTheme {
    Card(
        content = {
            DemoCard(userName = "logickoder", demoBalance = 50000f, currency = "$")
        }
    )
}