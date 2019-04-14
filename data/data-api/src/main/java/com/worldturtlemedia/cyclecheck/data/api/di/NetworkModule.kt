package com.worldturtlemedia.cyclecheck.data.api.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.worldturtlemedia.cyclecheck.data.api.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(
    includes = [
        MoshiModule::class
    ]
)
internal class NetworkModule {

    @Provides @Singleton
    fun provideOkHttpBuilder() = OkHttpClient.Builder().apply {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.BASIC

        addNetworkInterceptor(HttpLoggingInterceptor().also { it.level = level })

        connectTimeout(30, TimeUnit.SECONDS)
        writeTimeout(15, TimeUnit.SECONDS)
        readTimeout(15, TimeUnit.SECONDS)
    }

    @Provides @Singleton
    fun provideOkHttpClient(okHttpBuilder: OkHttpClient.Builder):
        OkHttpClient = okHttpBuilder.build()

    companion object {

        internal fun createRetrofitClient(
            okHttpClient: OkHttpClient,
            moshi: Moshi,
            baseUrl: String
        ): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}
