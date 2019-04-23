package com.worldturtlemedia.cyclecheck.models.weather

import org.joda.time.DateTime

data class CurrentWeather(
    val currentTemperature: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val cloudPercent: Int,
    val wind: Wind,
    val condition: WeatherCondition,
    val updatedAt: DateTime
)
