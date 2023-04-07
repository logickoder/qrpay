package dev.logickoder.qrpay.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val TintedText = Color(0xFFA1ACB8)

private val primaryColor = Color(0xFF696CFF)

internal val darkColorScheme = darkColorScheme(primary = primaryColor)

internal val lightColorScheme = lightColorScheme(
    primary = primaryColor,
    primaryContainer = Color(0xFFE7E7FF),
    secondary = Color(0xFF71DD37),
    secondaryContainer = Color(0xFF566A7F),
    background = Color(0xFFF5F5F9),
    error = Color(0xFFFF3E1D),
    onSurface = Color(0xFF697A8D),
    onBackground = Color(0xFF697A8D),
    onSecondary = Color.White,
)