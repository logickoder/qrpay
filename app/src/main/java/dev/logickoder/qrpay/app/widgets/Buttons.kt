package dev.logickoder.qrpay.app.widgets

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.logickoder.qrpay.R

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
    shape = MaterialTheme.shapes.medium,
    colors = ButtonDefaults.buttonColors(
        containerColor = color ?: MaterialTheme.colorScheme.primary
    ),
    content = {
        if (isLoading) Text(stringResource(id = R.string.please_wait)) else content()
    }
)
