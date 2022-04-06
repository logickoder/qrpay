package dev.logickoder.qrpay.utils

import java.text.DecimalFormat

/**
 * Returns a formatted decimal value
 */
val Double.formatted: String
    get() = DecimalFormat("#,###.00").format(this)

fun Double.formattedWith(currency: String) = "$currency $formatted"
