package com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap

import com.worldturtlemedia.cyclecheck.core.network.APIResult
import com.worldturtlemedia.cyclecheck.data.api.sources.weather.WeatherDataSource
import com.worldturtlemedia.cyclecheck.data.api.sources.weather.model.CurrentWeather
import javax.inject.Inject

internal class OpenWeatherApiSource @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
) : WeatherDataSource {

    override fun getCurrentWeather(): APIResult<CurrentWeather> {
        TODO("not implemented")
    }
}
