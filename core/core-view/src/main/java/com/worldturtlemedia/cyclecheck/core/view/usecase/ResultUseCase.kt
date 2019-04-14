package com.worldturtlemedia.cyclecheck.core.view.usecase

import com.worldturtlemedia.cyclecheck.core.network.APIResult
import kotlinx.coroutines.CancellationException
import timber.log.Timber

/**
 * A implementation of [AsyncUseCase] that wraps the return value in a [APIResult].  Which has the benefit
 * of being able to catch any uncaught exceptions, and returning a [APIResult.Error].
 *
 * @param[Params] Parameters required for executing use case.
 * @param[R] Return type of the use-case which will be wrapped in a [Result].
 */
abstract class ResultUseCase<Params, R> : AsyncUseCase<Params, APIResult<R>>() {

    /**
     * Invoke the use-case and catch any uncaught exceptions and return them as a [APIResult.Error].
     *
     * If [logException] is enabled any errors will be logged.
     *
     * @param[params] Use-case parameters.
     * @return The result of [execute] wrapped in a [APIResult].
     */
    override suspend operator fun invoke(params: Params): APIResult<R> = try {
        execute(params)
    } catch (error: Throwable) {
        if (error !is CancellationException && logException) {
            Timber.e(error, "Use-case failed")
        }

        APIResult.Error(error)
    }
}
