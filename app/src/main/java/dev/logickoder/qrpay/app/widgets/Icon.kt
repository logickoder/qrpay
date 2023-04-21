package dev.logickoder.qrpay.app.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val IconPadding = 8.dp

@Composable
fun Icon(
    icon: ImageVector,
    modifier: Modifier = Modifier
) = androidx.compose.material3.Icon(
    modifier = modifier
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(IconPadding),
    imageVector = icon,
    tint = MaterialTheme.colorScheme.primary,
    contentDescription = null
)
