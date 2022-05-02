package dev.logickoder.qrpay.data.api

import dev.logickoder.qrpay.data.api.params.*
import retrofit2.http.Body
import retrofit2.http.POST

interface QrPayApi {
    @POST("user-data")
    suspend fun login(@Body body: UserInfo): LoginResponse

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse

    @POST("send-money")
    suspend fun sendMoney(@Body body: SendMoney)

    @POST("user-transactions")
    suspend fun getTransactions(@Body body: UserInfo): Transactions

    companion object {
        const val BASE_URL = "https://demo.logad.net/qrpay/api/"
    }
}