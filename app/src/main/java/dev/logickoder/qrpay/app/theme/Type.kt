package dev.logickoder.qrpay.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily

val Typography: Typography
    get() {
        val typography = Typography()
        val fontFamily = FontFamily.SansSerif

        return Typography(
            displayLarge = typography.displayLarge.copy(fontFamily = fontFamily),
            displayMedium = typography.displayMedium.copy(fontFamily = fontFamily),
            displaySmall = typography.displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = typography.headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = typography.headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = typography.headlineMedium.copy(fontFamily = fontFamily),
            titleLarge = typography.titleLarge.copy(fontFamily = fontFamily),
            titleMedium = typography.titleMedium.copy(fontFamily = fontFamily),
            titleSmall = typography.titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = typography.bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = typography.bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = typography.bodySmall.copy(fontFamily = fontFamily),
            labelLarge = typography.labelLarge.copy(fontFamily = fontFamily),
            labelMedium = typography.labelMedium.copy(fontFamily = fontFamily),
            labelSmall = typography.labelSmall.copy(fontFamily = fontFamily),
        )
    }
