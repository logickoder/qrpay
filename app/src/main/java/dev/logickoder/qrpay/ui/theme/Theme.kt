package dev.logickoder.qrpay.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

typealias Theme = MaterialTheme

@Composable
fun QRPayTheme(content: @Composable () -> Unit) = MaterialTheme(
    colors = Colors,
    typography = Typography,
    shapes = Shapes,
    content = content
)
