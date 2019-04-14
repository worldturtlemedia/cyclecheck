package com.worldturtlemedia.cyclecheck.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Provide coroutines context, to allow for easier mocking while testing
 */
data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
)
