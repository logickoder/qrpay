package dev.logickoder.qrpay.sendmoney

import dev.logickoder.qrpay.model.Response

data class SendMoneyState(
    val amount: Float = 0f,
    val recipient: String = "",
    val note: String = "",
    val method: SendMoneyMethod = SendMoneyMethod.Username,
    val enabled: Boolean = false,
    val loading: Boolean = false,
    val apiResponse: Response<String>? = null,
)
