package com.worldturtlemedia.cyclecheck.data.repository

import com.worldturtlemedia.cyclecheck.data.api.APIModule
import dagger.Module

@Module(
    includes = [
        APIModule::class
    ]
)
abstract class RepositoryModule
