package com.worldturtlemedia.cyclecheck.core.network

import com.github.ajalt.timberkt.Timber.e
import okhttp3.Response

/**
 * A generic result class for API requests
 *
 * @param[R] Type of the [APIResult]
 */
sealed class APIResult<out R> {

    data class Success<out T>(val data: T) : APIResult<T>()

    data class Error(
        val exception: Throwable,
        val response: Response? = null,
        private val log: Boolean = false
    ) : APIResult<Nothing>() {

        init {
            if (log) {
                e(exception)
            }
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception, code=${errorCode ?: "n/a"}"
        }
    }
}

/**
 * `true` if [APIResult] is of type [Success] & holds non-null [Success.data].
 */
val APIResult<*>.succeeded: Boolean
    get() = this is APIResult.Success && data != null

val APIResult.Error.errorCode: Int?
    get() = response?.code()

val APIResult.Error.isClientError: Boolean
    get() = response?.code()?.let { HttpCode.isClientError(it) } ?: false

val APIResult.Error.isServerError: Boolean
    get() = response?.code()?.let { HttpCode.isServerError(it) } ?: false

fun <T> T.asAPIResultSuccess() = APIResult.Success(this)

fun Throwable.asAPIResultError() = APIResult.Error(this)

fun <T> APIResult<T>.dataOrThrow(): T = when (this) {
    is APIResult.Success -> data
    is APIResult.Error -> throw exception
}

fun <T> APIResult<T>.dataOrNull() = if (this is APIResult.Success) data else null

inline fun <T> APIResult<T>.onSuccess(block: (data: T) -> Unit): APIResult<T> = also { result ->
    if (result is APIResult.Success) block(result.data)
}

inline fun <T> APIResult<T>.onError(
    block: (exception: Throwable, response: Response?) -> Unit
): APIResult<T> = also { result ->
    if (result is APIResult.Error) block(result.exception, result.response)
}
