package dev.logickoder.qrpay.data.remote

import dev.logickoder.qrpay.data.remote.params.LoginResponse
import dev.logickoder.qrpay.data.remote.params.RegisterRequest
import dev.logickoder.qrpay.data.remote.params.RegisterResponse
import dev.logickoder.qrpay.data.remote.params.SendMoney
import dev.logickoder.qrpay.data.remote.params.Transactions
import dev.logickoder.qrpay.data.remote.params.UserInfo
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

object QrPayApi {
    private const val BASE_URL = "https://demo.logad.net/qrpay/api"

    suspend fun login(userInfo: UserInfo): ResultWrapper<LoginResponse> {
        return RemoteClient.call {
            post("$BASE_URL/user-data") {
                setBody(userInfo)
            }.body()
        }
    }

    suspend fun register(request: RegisterRequest): ResultWrapper<RegisterResponse> {
        return RemoteClient.call {
            post("$BASE_URL/register") {
                setBody(request)
            }.body()
        }
    }

    suspend fun sendMoney(request: SendMoney): ResultWrapper<Unit> {
        return RemoteClient.call {
            post("$BASE_URL/send-money") {
                setBody(request)
            }.body()
        }
    }

    suspend fun getTransactions(request: UserInfo): ResultWrapper<Transactions> {
        return RemoteClient.call {
            post("$BASE_URL/user-transactions") {
                setBody(request)
            }.body()
        }
    }

    private val RemoteClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(message)
                }
            }
            level = LogLevel.BODY
        }
        install(HttpTimeout) {
            val timeout = 10_000L
            socketTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout
        }
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    private suspend fun <T> HttpClient.call(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend HttpClient.() -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(this@call.apiCall())
            } catch (e: RedirectResponseException) {
                // 3xx - responses
                Napier.e(e.response.status.description)
                ResultWrapper.Failure(e)
            } catch (e: ClientRequestException) {
                // 4xx - responses
                Napier.e(e.response.status.description)
                ResultWrapper.Failure(e)
            } catch (e: ServerResponseException) {
                // 5xx - responses
                Napier.e(e.response.status.description)
                ResultWrapper.Failure(e)
            } catch (throwable: Throwable) {
                Napier.e(throwable.message.toString())
                ResultWrapper.Failure(throwable)
            }
        }
    }
}