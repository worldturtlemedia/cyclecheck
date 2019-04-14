package com.worldturtlemedia.cyclecheck.data.api.adapters

import com.squareup.moshi.JsonQualifier

/**
 * Indicates an endpoint wraps a response in a JSON Object.
 * When deserializer the response we should only return
 * what's inside the outer most object.
 *
 * Example:
 *
 * interface TradierApi {
 *   @Enveloped
 *   @GET("markets/lookup")
 *   fun lookup(@Query("q") symbol: String): Call<List<Securities>>
 * }
 *
 * @see https://medium.com/@naturalwarren/moshi-made-simple-jsonqualifier-b99559c826ad
 */
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
internal annotation class Enveloped
