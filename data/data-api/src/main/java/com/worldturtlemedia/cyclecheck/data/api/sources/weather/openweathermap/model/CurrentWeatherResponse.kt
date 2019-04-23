package com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class CurrentWeatherResponse(
    val id: Int,
    val name: String,
    @Json(name = "dt") val updatedAt: Double,
    @Json(name = "coord") val coordinates: Coordinates,
    @Json(name = "main") val weather: Weather,
    val wind: WindDetails,
    @Json(name = "weather") val conditions: List<WeatherCondition>,
    @Json(name = "sys") val misc: MiscDetails
)

@JsonSerializable
internal data class Coordinates(
    @Json(name = "lat") val latitude: Long,
    @Json(name = "lib") val longitude: Long
)

@JsonSerializable
internal data class Weather(
    @Json(name = "temp") val temperature: Double,
    @Json(name = "temp_min") val minTemp: Double,
    @Json(name = "temp_max") val maxTemp: Double,
    val humidity: Double,
    val pressure: Double
)

@JsonSerializable
internal data class WindDetails(
    val speed: Long,
    @Json(name = "deg") val degree: Long
)

internal data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String
)

internal data class MiscDetails(
    val sunrise: Double,
    val sunset: Double
)
