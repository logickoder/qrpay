package dev.logickoder.qrpay.ui.shared.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.logickoder.qrpay.ui.theme.Theme

val IconPadding = 8.dp

@Composable
fun Icon(
    icon: ImageVector,
    modifier: Modifier = Modifier
) = androidx.compose.material.Icon(
    modifier = modifier
        .clip(Theme.shapes.medium)
        .background(Theme.colors.primaryVariant)
        .padding(IconPadding),
    imageVector = icon,
    tint = Theme.colors.primary,
    contentDescription = null
)
