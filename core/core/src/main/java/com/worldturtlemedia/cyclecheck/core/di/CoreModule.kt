package com.worldturtlemedia.cyclecheck.core.di

import com.worldturtlemedia.cyclecheck.core.coroutines.CoroutinesDispatcherProviderModule
import com.worldturtlemedia.cyclecheck.core.di.viewmodel.ViewModelModule
import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        CoroutinesDispatcherProviderModule::class
    ]
)
abstract class CoreModule
