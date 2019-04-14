package com.worldturtlemedia.cyclecheck.data.api

import com.worldturtlemedia.cyclecheck.data.api.di.NetworkModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class
    ]
)
abstract class APIModule
