package dev.logickoder.qrpay.app.data.remote

/**
 * Represents the result of an api call
 */
sealed class ResultWrapper<out T> {
    /**
     * No result has been received
     * */
    object Loading : ResultWrapper<Nothing>()

    /**
     * The call was a success
     * @param data The data that is to be returned in a successful call event
     * */
    data class Success<T>(val data: T) : ResultWrapper<T>()

    /**
     * The call failed
     * @param error The exception thrown on a failed call event
     * */
    open class Failure(val error: Throwable) : ResultWrapper<Nothing>() {
        constructor(message: String) : this(Throwable(message))
    }
}

inline fun <T, S, R : ResultWrapper<S>> ResultWrapper<T>.successful(action: (T) -> R) =
    when (this) {
        is ResultWrapper.Success -> action(data)
        ResultWrapper.Loading -> ResultWrapper.Loading
        is ResultWrapper.Failure -> ResultWrapper.Failure(error)
    }