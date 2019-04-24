package com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap

import com.worldturtlemedia.cyclecheck.core.network.APIResult
import com.worldturtlemedia.cyclecheck.data.api.sources.weather.WeatherDataSource
import com.worldturtlemedia.cyclecheck.data.api.util.awaitMappedResult
import com.worldturtlemedia.cyclecheck.models.weather.CurrentWeather
import javax.inject.Inject

internal class OpenWeatherApiSource @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
) : WeatherDataSource {

    override suspend fun getCurrentWeather(
        latitude: Long,
        longitude: Long
    ): APIResult<CurrentWeather> = openWeatherApi
        .getCurrentWeatherFromLatLng(latitude, longitude)
        .awaitMappedResult()
}
