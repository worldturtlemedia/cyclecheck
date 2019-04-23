package com.worldturtlemedia.cyclecheck.data.api.sources.weather.model

import org.joda.time.DateTime

// TODO: Move to repository module

data class CurrentWeather(
    val currentTemperature: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val cloudPercent: Int,
    val wind: Wind,
    val condition: WeatherCondition,
    val updatedAt: DateTime
)
