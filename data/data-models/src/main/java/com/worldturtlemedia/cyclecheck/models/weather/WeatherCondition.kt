package com.worldturtlemedia.cyclecheck.models.weather

sealed class WeatherCondition

sealed class Thunderstorm : WeatherCondition() {
    object LightRain : Thunderstorm() // wi-day-lightning
    object Drizzle : Thunderstorm() // wi-day-snow-thunderstorm
    object Showers : Thunderstorm() // wi-day-storm-showers
    object Heavy : Thunderstorm() // wi-day-thunderstorm
}

sealed class Rain : WeatherCondition() {
    object Drizzle : Rain()
    object Showers : Rain()
    object Sprinkle : Rain()
    object Normal : Rain()
    object Mixed : Rain()
}

sealed class Snow : WeatherCondition() {
    object Normal : Snow()
    object Sleet : Snow()
    object Hail : Snow()
}

sealed class Misc : WeatherCondition() {
    object Cloud : Misc()
    object Cloudy : Misc()
    object Fog : Misc()
    object Smoke : Misc()
}

sealed class Clear : WeatherCondition()
