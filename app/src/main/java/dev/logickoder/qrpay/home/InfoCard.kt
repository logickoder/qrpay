package dev.logickoder.qrpay.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.qrpay.app.theme.QRPayTheme
import dev.logickoder.qrpay.app.theme.paddingMedium
import dev.logickoder.qrpay.app.widgets.Card
import dev.logickoder.qrpay.app.widgets.Icon
import dev.logickoder.qrpay.app.widgets.nonExistent


@Composable
fun <T> InfoCard(
    @StringRes title: Int,
    content: T?,
    @StringRes caption: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        content = {
            Row(
                content = {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = title),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Icon(
                        icon = icon,
                    )
                }
            )
            Text(
                modifier = Modifier.nonExistent(content),
                text = content.toString(),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.secondaryContainer,
            )
            Spacer(modifier = Modifier.height(paddingMedium()))
            Text(
                text = stringResource(id = caption),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    )
}


@Composable
@Preview(showBackground = true)
private fun InfoCardPreview() = QRPayTheme {
    Card(
        content = {
            InfoCard(
                title = 0,
                content = "logickoder",
                caption = 0,
                icon = Icons.Default.ConfirmationNumber
            )
        }
    )
}