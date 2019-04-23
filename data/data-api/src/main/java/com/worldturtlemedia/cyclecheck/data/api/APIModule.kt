package com.worldturtlemedia.cyclecheck.data.api

import com.worldturtlemedia.cyclecheck.data.api.di.NetworkModule
import com.worldturtlemedia.cyclecheck.data.api.sources.weather.WeatherSourceModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        WeatherSourceModule::class
    ]
)
abstract class APIModule
