package com.worldturtlemedia.cyclecheck.core.view.usecase

import com.worldturtlemedia.cyclecheck.core.network.APIResult
import com.worldturtlemedia.cyclecheck.core.util.Async
import com.worldturtlemedia.cyclecheck.core.util.Fail
import com.worldturtlemedia.cyclecheck.core.util.Loading
import com.worldturtlemedia.cyclecheck.core.util.Success
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A more advanced version of [AsyncUseCase] for getting "live" updates of a data call.
 *
 * Uses kotlin coroutine [Channel]s to provide a 'live stream' of the [Async] events.
 * By default [Channel.send] will be invoked with [Loading] when the use-case is invoked,
 * however this can be disabled by overriding [enableInitialLoading] to false.
 *
 * To update the channel, you can use the helper functions [loading], [success], [fail].  Or you
 * can just directly call [Channel.send] with your [Async] value.
 *
 * By default any new calls to [invoke] will NOT cancel the existing [Job].  You can change this by
 * overriding [cancelExistingJob], or by passing `cancelExistingJob = true` to [invoke].
 *
 * Example:
 *
 * ```
 * class SampleUseCase(
 *    private val sampleRepository: SampleRepository
 * ) : LiveUseCase<String, Boolean>() {
 *
 *    // set to false to disable automatically sending 'Loading'
 *    override val enableInitialLoading = true
 *
 *    // Cancel the current job before launching the new one
 *    override val cancelExistingJob = true
 *
 *    override suspend fun execute(params: String) {
 *       // if you set "enableInitialLoading" to false, you have to manually invoke "Loading"
 *       // channel.loading() // If you want to
 *
 *       val result = sampleRepository.loadSomeData(params)
 *       when (result) {
 *          is Result.Successful -> channel.success(result.someBooleanData)
 *          is Result.Error -> channel.fail(result.error)
 *       }
 *    }
 * }
 * ```
 *
 * @param[Params] Type of the parameters the use-case accepts.
 * @param[R] Return type for the [Async].
 */
abstract class LiveUseCase<in Params, R> : AsyncUseCase<Params, R>() {

    /**
     * [Channel] to send the async updates to.
     */
    protected val channel: Channel<Async<R>> = Channel(capacity = Channel.UNLIMITED)

    /**
     * Override to `false` if you don't want [loading] to be called when the use-case is invoked.
     */
    protected open val enableInitialLoading: Boolean = true

    /**
     * Default value for `cancelExistingJob` on [invoke].
     */
    protected open val cancelExistingJob: Boolean = false

    /**
     * [Job] reference to hold onto the coroutine so it can be cancelled if needed.
     */
    private var job: Job = Job()

    /**
     * Override the default [AsyncUseCase.execute], so that children of [LiveUseCase] can call [liveExecute] instead.
     *
     * @throws NotImplementedError
     * @return Nothing.
     */
    @Throws(NotImplementedError::class)
    override suspend fun execute(params: Params): R = throw NotImplementedError(
        "LiveUseCase should not be calling `execute` it should be calling `liveExecute"
    )

    /**
     * The actual implementation of the use-case, do your suspended fun here.
     *
     * @throws RuntimeException
     * @param[params] Parameters to execute the use-case.
     */
    @Throws(RuntimeException::class)
    abstract suspend fun liveExecute(params: Params)

    /**
     * Invoke the use-case and call [executeUseCase].
     *
     * Create a coroutine channel using the passed in scope.
     *
     * @param[params] Parameters for the use-case.
     * @param[cancelExistingJob] If true, cancel the existing job if it exists.
     * @return A receive only version of the [channel].
     */
    @ExperimentalCoroutinesApi
    open suspend operator fun invoke(
        params: Params,
        cancelExistingJob: Boolean = this.cancelExistingJob
    ): ReceiveChannel<Async<R>> {
        if (cancelExistingJob) job.cancel()

        job = CoroutineScope(dispatcher).launch {
            executeUseCase(params)
        }

        if (enableInitialLoading) channel.loading()

        return channel
    }

    /**
     * Convenience function to send a [Loading] to the [channel].
     */
    protected suspend fun Channel<Async<R>>.loading() = send(Loading())

    /**
     * Convenience function to send a [Fail] to the [channel].
     *
     * @param[error] Exception to attach to the [Fail].
     * @param[data] Optional data of type [R].
     */
    protected suspend fun Channel<Async<R>>.fail(
        error: Throwable,
        data: R? = null
    ) = send(Fail(error, data))

    /**
     * Convenience function to send  a [Success] with [result] to the [channel].
     *
     * @param[result] Result of the async call.
     */
    protected suspend fun Channel<Async<R>>.success(result: R) = send(Success(result))

    /**
     * Convenience function to handle a [Result] with [Channel] calls.
     *
     * @receiver [Result] containing status of an async call.
     * @param[logException] If true log the [Result.Error.exception] using Timber.
     */
    protected suspend fun APIResult<R>.handleResult(logException: Boolean = false) = when (this) {
        is APIResult.Success -> channel.success(data)
        is APIResult.Error -> {
            if (logException) Timber.e(exception, "Use-case Result failed!")
            channel.fail(exception)
        }
    }

    /**
     * Execute the use-case in a `try-catch` to catch any uncaught exceptions.
     *
     * @param[params] Use-case parameters to call [execute].
     */
    private suspend fun executeUseCase(params: Params) {
        try {
            liveExecute(params)
        } catch (error: Throwable) {
            when (error) {
                is CancellationException -> return
                is NotImplementedError -> throw error
                else -> {
                    if (logException) Timber.e(error)

                    channel.send(Fail(error))
                }
            }
        }
    }
}
