package dev.logickoder.qrpay.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

internal val Colors = lightColors(
    primary = Color(0xFF696CFF),
    primaryVariant = Color(0xFFE7E7FF),
    background = Color(0xFFF5F5F9),

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

object TextColors {
    val title = Color(0xFF566A7F)
    val body = Color(0xFF697A8D)
    val disabled = Color(0xFFA1ACB8)
}

val Colors.text: TextColors get() = TextColors
