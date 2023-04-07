package dev.logickoder.qrpay.ui.shared.composables

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.ui.theme.Theme

@Composable
fun LoadingButton(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) = Button(
    onClick = { onClick() },
    modifier = modifier,
    enabled = enabled && !isLoading,
    shape = Theme.shapes.medium,
    colors = ButtonDefaults.buttonColors(containerColor = color ?: Theme.colorScheme.primary),
    content = {
        if (isLoading) Text(stringResource(id = R.string.please_wait)) else content()
    }
)
