package com.worldturtlemedia.cyclecheck.models.weather

import org.joda.time.DateTime

data class CurrentWeather(
    val currentTemperature: Double,
    val cloudPercent: Double,
    val wind: Wind,
    val condition: WeatherCondition,
    val updatedAt: DateTime,
    val minTemp: Double? = null,
    val maxTemp: Double? = null
)
