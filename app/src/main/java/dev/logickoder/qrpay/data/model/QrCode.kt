package dev.logickoder.qrpay.data.model

import com.google.gson.Gson
import dev.logickoder.qrpay.data.api.params.UserInfo

typealias QrCode = UserInfo

val String.qrCode: QrCode
    get() = Gson().fromJson(this, QrCode::class.java)

val QrCode.json: String
    get() = Gson().toJson(this)