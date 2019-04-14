package com.worldturtlemedia.cyclecheck.core.view.usecase

import kotlinx.coroutines.CancellationException
import timber.log.Timber

/**
 * A implementation of [AsyncUseCase] that allows a nullable return value.
 *
 * It will return `null` if there is an unhandled exception.
 *
 * @param[Params] Parameters required for executing use case.
 * @param[R] Nullable return type of the use-case.
 */
abstract class NullableUseCase<Params, R> : AsyncUseCase<Params, R?>() {

    /**
     * Attempt to execute the use-case, and if the result is `null` then use [defaultValue] to
     * get a default return value.
     *
     * @param[params] Parameters required to execute the use-case.
     * @param[defaultValue] Lambda to provide a default value if the use-case returns `null`.
     */
    suspend operator fun invoke(
        params: Params,
        defaultValue: () -> R
    ): R = invoke(params) ?: defaultValue()

    /**
     * Attempt to execute the use-case and return the result.  If the use-case fails because
     * of an uncaught exception, a `null` value will be returned.
     *
     * @param[params] Parameters required to execute the use-case.
     */
    override suspend operator fun invoke(params: Params): R? = try {
        execute(params)
    } catch (error: Throwable) {
        if (error !is CancellationException && logException) {
            Timber.e(error, "Use-case failed")
        }

        null
    }
}
