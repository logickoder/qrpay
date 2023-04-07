package dev.logickoder.qrpay.app.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.logickoder.qrpay.app.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Action(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = Scaffold(
    modifier = modifier,
    topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = title)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Theme.colorScheme.surface,
            ),
        )
    },
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        content()
    }
}
