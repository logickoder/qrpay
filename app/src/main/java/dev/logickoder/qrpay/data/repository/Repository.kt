package dev.logickoder.qrpay.data.repository

import dev.logickoder.qrpay.data.api.QrPayApi

abstract class Repository(val remote: QrPayApi) {
    abstract suspend fun clear()
}
