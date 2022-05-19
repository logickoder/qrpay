package dev.logickoder.qrpay.ui.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
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
                .background(Theme.colors.error)
                .size(size),
            imageVector = Icons.Outlined.Error,
            colorFilter = ColorFilter.tint(color = Theme.colors.onError),
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.secondary_padding) / 2
            )
        )
        Text(
            text = error,
            style = Theme.typography.body2,
            textAlign = TextAlign.Center,
        )
    }
}
