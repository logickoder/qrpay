package dev.logickoder.qrpay.app.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.logickoder.qrpay.app.theme.Theme
import dev.logickoder.qrpay.app.theme.paddingPrimary

val CardElevation = 4.dp

@Composable
fun Card(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = androidx.compose.material3.Card(
    modifier = modifier,
    shape = Theme.shapes.large,
    elevation = CardDefaults.cardElevation(
        defaultElevation = CardElevation,
    ),
    colors = CardDefaults.cardColors(
        Theme.colorScheme.onPrimary,
    ),
) {
    Box(
        modifier = Modifier.padding(paddingPrimary())
    ) {
        content()
    }
}
