package dev.logickoder.qrpay.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.Failure.NetworkError
                is HttpException -> ResultWrapper.Failure.GenericError(throwable.code(), throwable)
                else -> ResultWrapper.Failure.GenericError(null, throwable)
            }
        }
    }
}