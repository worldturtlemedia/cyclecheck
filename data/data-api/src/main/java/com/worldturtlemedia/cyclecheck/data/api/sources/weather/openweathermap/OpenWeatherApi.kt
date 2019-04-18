package com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap

import com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap.model.CurrentWeatherResponse
import com.worldturtlemedia.cyclecheck.data.api.util.APIResponse
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface OpenWeatherApi {

    @GET("weather")
    fun getCurrentWeatherFromLatLng(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Long
    ): APIResponse<CurrentWeatherResponse>

    companion object {

        const val BASE_URL: String = "api.openweathermap.org/data/2.5/"

        const val QUERY_AUTH: String = "appid"

        class AddOpenWeatherApiKeyInterceptor(private val apiKey: String) : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response =
                chain.request().url().newBuilder().addQueryParameter(QUERY_AUTH, apiKey).build()
                    .let { url ->
                        chain.proceed(chain.request().newBuilder().url(url).build())
                    }
        }
    }
}
