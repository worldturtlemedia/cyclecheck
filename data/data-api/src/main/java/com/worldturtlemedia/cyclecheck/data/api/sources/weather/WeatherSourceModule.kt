package com.worldturtlemedia.cyclecheck.data.api.sources.weather

import com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap.di.OpenWeatherMapModule
import dagger.Module

@Module(
    includes = [
        OpenWeatherMapModule::class
    ]
)
class WeatherSourceModule
