package dev.logickoder.qrpay.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

internal val Colors = lightColors(
    primary = Color(0xFF696CFF),
    primaryVariant = Color(0xFFE7E7FF),
    secondary = Color(0xFF71DD37),
    background = Color(0xFFF5F5F9),
    error = Color(0xFFFF3E1D),
    onSurface = Color(0xFF697A8D),
    onSecondary = Color.White,

    /* Other default colors to override
        surface = Color.White,
        onPrimary = Color.White,
        onBackground = Color.Black,
    */
)

object TextColors {
    val title = Color(0xFF566A7F)
    val disabled = Color(0xFFA1ACB8)
}

val Colors.text: TextColors get() = TextColors
