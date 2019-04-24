package com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap.model

import com.squareup.moshi.Json
import com.worldturtlemedia.cyclecheck.core.network.Mapper
import com.worldturtlemedia.cyclecheck.models.weather.Clear
import com.worldturtlemedia.cyclecheck.models.weather.CurrentWeather
import com.worldturtlemedia.cyclecheck.models.weather.Misc
import com.worldturtlemedia.cyclecheck.models.weather.Rain
import com.worldturtlemedia.cyclecheck.models.weather.Snow
import com.worldturtlemedia.cyclecheck.models.weather.Thunderstorm
import com.worldturtlemedia.cyclecheck.models.weather.WeatherCondition
import com.worldturtlemedia.cyclecheck.models.weather.Wind
import org.joda.time.DateTime
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class CurrentWeatherResponse(
    val id: Int,
    val name: String,
    @Json(name = "dt") val updatedAt: Double,
    @Json(name = "coord") val coordinates: Coordinates,
    @Json(name = "main") val weather: Weather,
    val wind: WindDetails,
    val clouds: Clouds,
    @Json(name = "weather") val conditions: List<OWMCondition>,
    @Json(name = "sys") val misc: MiscDetails
) : Mapper<CurrentWeather> {

    override fun map() = CurrentWeather(
        currentTemperature = weather.temperature,
        cloudPercent = clouds.percent,
        wind = Wind(wind.speed, wind.degree),
        condition = conditions.firstOrNull()?.toWeatherCondition() ?: Clear,
        updatedAt = DateTime(updatedAt)
    )
}

@JsonSerializable
internal data class Clouds(
    @Json(name = "all") val percent: Double
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
    @Json(name = "deg") val degree: Int
)

internal data class MiscDetails(
    val sunrise: Double,
    val sunset: Double
)

internal data class OWMCondition(
    val id: Int,
    val main: String,
    val description: String
)

/**
 * Map OpenWeatherMap's weather condition to a [WeatherCondition].
 * https://openweathermap.org/weather-conditions
 *
 * @return The current weather condition.
 */
private fun OWMCondition.toWeatherCondition(): WeatherCondition = when (id) {
    in 200..232 -> {
        when (id) {
            200, 201 -> Thunderstorm.LightRain
            in 230..232 -> Thunderstorm.Drizzle
            210, 211 -> Thunderstorm.Showers
            else -> Thunderstorm.Heavy
        }
    }
    in 300..531 -> {
        when (id) {
            in 300..311 -> Rain.Drizzle
            in 312..314 -> Rain.Mixed
            321, in 520..531 -> Rain.Showers
            500, 501 -> Rain.Sprinkle
            else -> Rain.Normal
        }
    }
    in 600..622 -> {
        when (id) {
            in 611..613 -> Snow.Sleet
            else -> Snow.Normal
        }
    }
    in 700..781 -> {
        when (id) {
            711 -> Misc.Smoke
            else -> Misc.Fog
        }
    }
    801, 802 -> Misc.Cloud
    803, 804 -> Misc.Cloudy
    else -> Clear
}
