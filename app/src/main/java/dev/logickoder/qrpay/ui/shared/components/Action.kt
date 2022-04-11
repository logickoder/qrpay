package dev.logickoder.qrpay.ui.shared.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.theme.Theme

@Composable
fun Action(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) = Scaffold(
    modifier = modifier,
    topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = title),
                    color = Theme.colors.secondary,
                )
            },
            backgroundColor = Theme.colors.surface,
            actions = {
                action?.let {
                    it()
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.primary_padding) / 2))
                }
            }
        )
    },
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        content()
    }
}
