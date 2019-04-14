package com.worldturtlemedia.cyclecheck.data.api.util

import com.worldturtlemedia.cyclecheck.core.network.APIResult
import com.worldturtlemedia.cyclecheck.core.network.Mapper
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Await the [Response] of a [Deferred] network call, transforming the response into a [APIResult].
 *
 * The `await` is wrapped in a `try-catch` and will return a [APIResult.Error] if the network request
 * fails for IO reasons (lack of internet, etc).  It is up to the [transform] lambda to handle all
 * of the possible HTTP codes.  [awaitResult] is a default implementation that handles the generic errors codes.
 *
 * @param[RESPONSE] Type of the [Response].
 * @param[RESULT] Type of the intended [APIResult].
 * @param[transform] Lambda expression to transform the [Response] to a [APIResult].
 * @return [RESULT] value wrapped in a [APIResult].
 */
suspend inline fun <RESPONSE, RESULT> Deferred<Response<RESPONSE>>.awaitResponse(
    crossinline transform: (response: Response<RESPONSE>) -> APIResult<RESULT>
): APIResult<RESULT> {
    return try {
        await().let(transform)
    } catch (error: Throwable) {
        APIResult.Error(exception = IOException("Error awaiting request", error), log = true)
    }
}

/**
 * Handle the success and error states of a [Deferred] [Response].
 *
 * Takes a [transform] lambda which will map the [RESPONSE] into the desired [RESULT]
 * It will create the appropriate error for each situation.
 *
 * @param[RESPONSE] Type of the [Response].
 * @param[RESULT] Type of the intended [APIResult].
 * @param transform Function to map the response into the result.
 * @return [APIResult] containing the mapped data or the exception.
 */
suspend inline fun <RESPONSE, RESULT> Deferred<Response<RESPONSE>>.awaitResult(
    crossinline transform: (response: RESPONSE) -> RESULT
): APIResult<RESULT> = awaitResponse { response ->
    if (response.isSuccessful) {
        response.body()
            ?.let { APIResult.Success(transform(it)) }
            ?: APIResult.Error(
                exception = NullPointerException("Response body was null"),
                response = response.raw(),
                log = true
            )
    } else {
        APIResult.Error(HttpException(response), response.raw(), log = true)
    }
}

/**
 * Handle the success and error states of a [Deferred] [Response].
 *
 * A parameter-less version of [awaitResult], it passes in a callback that just
 * returns the data right away.  Useful if you do not need to map the response into
 * a new type
 *
 * @param[RESPONSE] Type of the [Response] from the API.
 * @return [APIResult] containing the data or the exception.
 */
suspend fun <RESPONSE> Deferred<Response<RESPONSE>>.awaitResult(): APIResult<RESPONSE> =
    awaitResult { it }

/**
 * Await the response of the [Deferred] which inherits from [Mapper], when successful invoke [Mapper.map].
 *
 * @param[T] Type of the [APIResult] that is mappable via [Mapper].
 * @param[RESULT] Type of the [APIResult].
 * @return A [APIResult] mapped to [T] via [Mapper.map].
 */
suspend fun <T : Mapper<RESULT>, RESULT> Deferred<Response<T>>.awaitMappedResult(): APIResult<RESULT> =
    awaitResult { it.map() }
