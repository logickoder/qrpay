package dev.logickoder.qrpay.app.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val darkColorScheme = darkColorScheme(
    primary = Color(0xFFA0A3FF),
    primaryContainer = Color(0xFF424242),
    secondary = Color(0xFF78B040),
    secondaryContainer = Color(0xFF757575),
    onTertiary = Color(0xFF6E7887),
    background = Color(0xFF1E1E1E),
    error = Color(0xFFFF3E1D),
    onSurface = Color.White,
    onBackground = Color.White,
    onSecondary = Color(0xFF202020)
)

internal val lightColorScheme = lightColorScheme(
    primary = Color(0xFF696CFF),
    primaryContainer = Color(0xFFE7E7FF),
    secondary = Color(0xFF71DD37),
    secondaryContainer = Color(0xFF566A7F),
    onTertiary = Color(0xFFA1ACB8),
    background = Color(0xFFF5F5F9),
    error = Color(0xFFFF3E1D),
    onSurface = Color(0xFF697A8D),
    onBackground = Color(0xFF697A8D),
    onSecondary = Color.White,
)