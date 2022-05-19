package dev.logickoder.qrpay.ui.shared.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.theme.Theme

val CardElevation = 4.dp

@Composable
fun Card(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = androidx.compose.material.Card(
    modifier = modifier,
    shape = Theme.shapes.large,
    elevation = CardElevation,
) {
    Box(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.primary_padding))
    ) {
        content()
    }
}
