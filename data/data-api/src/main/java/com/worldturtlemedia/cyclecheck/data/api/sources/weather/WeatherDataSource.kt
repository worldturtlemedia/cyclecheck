package com.worldturtlemedia.cyclecheck.data.api.sources.weather

import com.worldturtlemedia.cyclecheck.core.network.APIResult
import com.worldturtlemedia.cyclecheck.data.api.sources.weather.model.CurrentWeather

interface WeatherDataSource {

    suspend fun getCurrentWeather(latitude: Long, longitude: Long): APIResult<CurrentWeather>
}
