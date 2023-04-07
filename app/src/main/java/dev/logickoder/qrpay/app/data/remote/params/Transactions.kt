package dev.logickoder.qrpay.app.data.remote.params

import dev.logickoder.qrpay.app.data.model.Transaction

data class Transactions(
    val `data`: List<Data>,
    val error: Boolean,
    val message: String
) {
    data class Data(
        val amountFormatted: String,
        val dateFormatted: String,
        val description: String,
        val timeFormatted: String,
        val transId: String,
        val transType: String,
        val type: String,
        val typeText: String
    ) {
        val transaction: Transaction
            get() = Transaction(
                id = transId,
                date = "$dateFormatted $timeFormatted",
                amount = amountFormatted
                    .substring(1)
                    .replace(",", "")
                    .toDouble() * if (transType == "debit") -1 else 1,
                comment = description,
                type = type,
            )
    }
}