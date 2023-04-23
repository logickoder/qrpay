package dev.logickoder.qrpay.app.utils

import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

private val amountFormatter = DecimalFormat("#,###.00")
private val timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

/**
 * Returns a formatted amount
 */
val Float.formatted: String
    get() = amountFormatter.format(this)


/**
 * Returns a formatted time
 */
val LocalDateTime.formatted: String
    get() = format(timeFormatter)

/**
 * Returns a formatted decimal value with a [currency] in front
 */
fun Float.formatted(currency: String) = "$currency$formatted"

/**
 * true if the amount is lesser than 0
 */
val Float.isDebit: Boolean get() = this < 0f

/**
 * Returns a formatted amount, either in debit or credit mode with the currency in front
 */
fun Float.transaction(currency: String): String {
    return "${if (isDebit) "-" else "+"} $currency${absoluteValue.formatted}"
}