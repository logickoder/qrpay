package dev.logickoder.qrpay.ui.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.theme.Theme


@Composable
fun ErrorScreen(
    error: String, modifier: Modifier = Modifier
) = BoxWithConstraints(modifier = modifier) {
    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.secondary_padding))) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .background(Theme.colors.error)
                .padding(dimensionResource(id = R.dimen.secondary_padding) / 2),
            imageVector = Icons.Outlined.Error,
            colorFilter = ColorFilter.tint(color = Theme.colors.onError),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.primary_padding)))
        Text(text = error)
    }
}