package com.worldturtlemedia.cyclecheck.data.api.di

import com.squareup.moshi.Moshi
import com.worldturtlemedia.cyclecheck.data.api.adapters.ApplicationJsonAdapterFactory
import com.worldturtlemedia.cyclecheck.data.api.adapters.EnvelopeFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class MoshiModule {

    @Provides @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(ApplicationJsonAdapterFactory.INSTANCE)
            .add(EnvelopeFactory.INSTANCE)
            .build()
}
