package com.worldturtlemedia.cyclecheck.data.api.util

import kotlinx.coroutines.Deferred
import retrofit2.Response

typealias APIResponse<R> = Deferred<Response<R>>
