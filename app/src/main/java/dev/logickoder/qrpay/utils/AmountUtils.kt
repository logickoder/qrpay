package dev.logickoder.qrpay.utils

import java.text.DecimalFormat
import kotlin.math.absoluteValue

typealias Amount = Double

/**
 * Returns a formatted amount
 */
val Amount.formatted: String
    get() = DecimalFormat("#,###.00").format(this)

/**
 * Returns a formatted decimal value with a [currency] in front
 */
fun Amount.formattedWith(currency: String) = "$currency$formatted"

/**
 * true if the amount is lesser than 0
 */
val Amount.isDebit: Boolean get() = this < 0

/**
 * Returns a formatted amount, either in debit or credit mode with the currency in front
 */
fun Amount.formattedTransactionWith(currency: String): String {
    return "${if (isDebit) "-" else "+"} ${absoluteValue.formattedWith(currency)}"
}
