package com.worldturtlemedia.cyclecheck.core.network

import com.github.ajalt.timberkt.Timber.e
import com.worldturtlemedia.cyclecheck.core.network.APIResult.Success
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
    get() = this is Success && data != null

val APIResult.Error.errorCode: Int?
    get() = response?.code()

val APIResult.Error.isClientError: Boolean
    get() = response?.code()?.let { HttpCode.isClientError(it) } ?: false

val APIResult.Error.isServerError: Boolean
    get() = response?.code()?.let { HttpCode.isServerError(it) } ?: false

fun <T> T.asAPIResultSuccess() = APIResult.Success(this)

fun Throwable.asAPIResultError() = APIResult.Error(this)
