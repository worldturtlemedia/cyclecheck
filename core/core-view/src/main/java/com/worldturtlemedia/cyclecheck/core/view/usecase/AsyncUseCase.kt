package com.worldturtlemedia.cyclecheck.core.view.usecase

import com.worldturtlemedia.cyclecheck.core.view.BuildConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A basic Use-case class for async tasks using Kotlin coroutines.
 *
 * This implementation includes no safety, so you must wrap any call that can throw into a `try-catch`.
 * If you do not need any async capabilities then consider using a [UseCase] or [useCase].
 *
 * @see ResultUseCase for a use-case that handles exceptions.
 * @param[Params] Parameters required for executing use case.
 * @param[R] Return type of the use-case.
 * @param[dispatcher] [CoroutineDispatcher] for dispatching the [execute].  Defaults to the `IO` dispatcher.
 */
abstract class AsyncUseCase<in Params, out R>(
    protected open val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    /**
     * Enable/disable logging a caught exception from [execute].
     */
    protected open val logException: Boolean = BuildConfig.DEBUG

    /**
     * The actual implementation of the use-case, do your suspended fun here.
     * @throws RuntimeException
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: Params): R

    /**
     * Execute the use-case and return the result.
     *
     * @param[params] Use-case parameters.
     * @return The result of [execute].
     */
    open suspend operator fun invoke(params: Params): R = execute(params)
}

/**
 * Creates a simple implementation of a [AsyncUseCase].
 *
 * Example:
 *
 * ```
 * val dataRepository = DataRepository()
 * val fetchData = asyncUseCase<Int, String> { id ->
 *   dataRepository.someAsyncFetchCall(id)
 * }
 *
 * // Later in the code
 * val result = fetchData(myId)
 * ```
 *
 * @param[Params] Type of parameters for the use-case.
 * @param[R] Return type of the use-case.
 * @param[dispatcher] Provide a [CoroutineDispatcher] context for launching all the coroutines, defaults to the IO dispatcher.
 * @param[execute] Lambda expression for executing the [AsyncUseCase], supplies [Params] and expects [R].
 * @return A constructed [AsyncUseCase] that can be invoked.
 */
fun <Params, R> asyncUseCase(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    execute: suspend (params: Params) -> R
): AsyncUseCase<Params, R> {
    return object : AsyncUseCase<Params, R>(dispatcher) {

        override suspend fun execute(params: Params): R = execute(params)
    }
}
