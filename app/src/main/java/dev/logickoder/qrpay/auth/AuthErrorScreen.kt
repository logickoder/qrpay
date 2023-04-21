package dev.logickoder.qrpay.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import dev.logickoder.qrpay.app.theme.secondaryPadding
import dev.logickoder.qrpay.app.theme.smallPadding


@Composable
fun AuthErrorScreen(
    error: String,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.padding(secondaryPadding()),
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.error)
                            .size(this@BoxWithConstraints.maxWidth / 2),
                        imageVector = Icons.Outlined.Error,
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onError),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(smallPadding()))
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        }
    )
}
