package dev.logickoder.qrpay.app.data.remote

import dev.logickoder.qrpay.app.data.local.UserStore
import dev.logickoder.qrpay.model.Response
import dev.logickoder.qrpay.model.Transaction
import dev.logickoder.qrpay.model.User
import dev.logickoder.qrpay.model.dto.AuthResponse
import dev.logickoder.qrpay.model.dto.CreateUserRequest
import dev.logickoder.qrpay.model.dto.LoginRequest
import dev.logickoder.qrpay.model.dto.SendMoneyRequest
import dev.logickoder.qrpay.model.isSuccessful
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class QrPayApi constructor(
    private val userStore: UserStore,
) {
    private val baseUrl = "qrpay-logickoder.azurewebsites.net/api"

    suspend fun login(body: LoginRequest): ResultWrapper<Response<AuthResponse>> {
        return client.call {
            post("$baseUrl/login") {
                setBody(body)
            }.body()
        }
    }

    private suspend fun refreshToken(body: AuthResponse): ResultWrapper<Response<AuthResponse>> {
        return client.call {
            post("$baseUrl/refresh-token") {
                setBody(body)
            }.body()
        }
    }

    suspend fun register(request: CreateUserRequest): ResultWrapper<Response<User>> {
        return client.call {
            post("$baseUrl/register") {
                setBody(request)
            }.body()
        }
    }

    suspend fun getUser(username: String): ResultWrapper<Response<User>> {
        return client.call {
            get("$baseUrl/users/$username").body()
        }
    }

    suspend fun sendMoney(request: SendMoneyRequest): ResultWrapper<Response<Transaction>> {
        return client.call {
            post("$baseUrl/transactions/send-money") {
                setBody(request)
            }.body()
        }
    }

    suspend fun getTransactions(): ResultWrapper<Response<List<Transaction>>> {
        return client.call {
            get("$baseUrl/transactions").body()
        }
    }

    private val client = HttpClient(OkHttp) {
        install(Auth) {
            bearer {
                loadTokens {
                    val token = userStore.getToken().first() ?: return@loadTokens null
                    BearerTokens(token, token)
                }
                refreshTokens {
                    if (oldTokens == null) return@refreshTokens null

                    when (
                        val result = refreshToken(AuthResponse(token = oldTokens!!.refreshToken))
                    ) {
                        is ResultWrapper.Success -> if (result.data.isSuccessful()) {
                            val token = result.data.data!!.token
                            BearerTokens(token, token)
                        } else null

                        else -> null
                    }
                }
            }
        }
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