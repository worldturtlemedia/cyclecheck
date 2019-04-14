package com.worldturtlemedia.cyclecheck.core.view.usecase

/**
 * A basic Use-case class for synchronous tasks.
 *
 * This implementation includes no safety, so you must wrap any call that can throw into a `try-catch`.
 *
 * Use [useCase] as a convenient builder.
 *
 * @see AsyncUseCase for a use-cases that require async features.
 * @param[Params] Parameters required for executing use case.
 * @param[R] Return type of the use-case.
 */
interface UseCase<Params, R> {

    /**
     * Invoke the use-case.
     *
     * *Note: there is no safety, and could potentially throw depending on your implementation.
     *
     * @param[params] Parameters required for executing use case.
     * @return Result of the use-case with a type of [R].
     */
    operator fun invoke(params: Params): R
}

/**
 * Creates a simple implementation of a [UseCase], for synchronous tasks.
 *
 * If you need async support then consider using [AsyncUseCase].
 *
 * *Note: Provides no error handling, ensure you are being safe.
 *
 * Example:
 *
 * ```
 * val dataRepository = DataRepository()
 * val fetchData = useCase<Int, String> { id ->
 *   dataRepository.someSynchronousCall(id)
 * }
 *
 * // Later in the code
 * val result = fetchData(myId)
 * ```
 *
 * @param[Params] Type of parameters for the use-case.
 * @param[R] Return type of the use-case.
 * @param[execute] Lambda expression for executing the [UseCase], supplies [Params] and expects [R].
 * @return A constructed [UseCase] that can be invoked.
 */
fun <Params, R> useCase(execute: (params: Params) -> R): UseCase<Params, R> {
    return object : UseCase<Params, R> {

        override operator fun invoke(params: Params): R = execute(params)
    }
}
