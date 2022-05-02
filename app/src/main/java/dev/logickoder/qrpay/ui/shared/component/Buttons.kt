package dev.logickoder.qrpay.ui.shared.component

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.theme.Theme

@Composable
fun LoadingButton(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) = Button(
    onClick = { onClick() },
    modifier = modifier,
    enabled = enabled && !isLoading,
    shape = Theme.shapes.medium,
    content = {
        if (isLoading) Text(stringResource(id = R.string.please_wait)) else content()
    }
)