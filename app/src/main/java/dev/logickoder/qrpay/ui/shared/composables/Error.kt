package dev.logickoder.qrpay.ui.shared.composables

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.theme.Theme


@Composable
fun Error(
    error: String, modifier: Modifier = Modifier
) = BoxWithConstraints(
    modifier = modifier.padding(dimensionResource(id = R.dimen.secondary_padding))
) {
    val size = maxWidth / 2
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .background(Theme.colorScheme.error)
                .size(size),
            imageVector = Icons.Outlined.Error,
            colorFilter = ColorFilter.tint(color = Theme.colorScheme.onError),
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.secondary_padding) / 2
            )
        )
        Text(
            text = error,
            style = Theme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}
