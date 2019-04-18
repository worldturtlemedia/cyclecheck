package com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap.di

import com.squareup.moshi.Moshi
import com.worldturtlemedia.cyclecheck.data.api.BuildConfig
import com.worldturtlemedia.cyclecheck.data.api.di.NetworkModule
import com.worldturtlemedia.cyclecheck.data.api.sources.weather.WeatherDataSource
import com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap.OpenWeatherApi
import com.worldturtlemedia.cyclecheck.data.api.sources.weather.openweathermap.OpenWeatherApiSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier annotation class OpenWeatherMapAPIKey
@Qualifier annotation class OpenWeatherAuthorizedOkHttp
@Qualifier annotation class OpenWeatherRetrofit
@Qualifier annotation class OpenWeatherMapSource

@Module
internal abstract class OpenWeatherMapModule {

    @Provides @OpenWeatherMapAPIKey
    fun provideOpenWeatherMapAPIKey(): String = BuildConfig.API_KEY_OPEN_WEATHER

    @Provides @OpenWeatherAuthorizedOkHttp
    fun provideOpenWeatherAuthorizedOkHttp(
        builder: OkHttpClient.Builder,
        @OpenWeatherMapAPIKey apiKey: String
    ): OkHttpClient = builder
        .addInterceptor(OpenWeatherApi.Companion.AddOpenWeatherApiKeyInterceptor(apiKey))
        .build()

    @Provides @OpenWeatherRetrofit @Singleton
    fun provideOpenWeatherRetrofit(
        @OpenWeatherAuthorizedOkHttp client: OkHttpClient,
        moshi: Moshi
    ): Retrofit = NetworkModule.createRetrofitClient(client, moshi, OpenWeatherApi.BASE_URL)

    @Provides @Singleton
    fun provideOpenWeatherMapApi(
        @OpenWeatherRetrofit retrofit: Retrofit
    ): OpenWeatherApi = retrofit.create()

    @Provides @OpenWeatherMapSource @Singleton
    fun provideOpenWeatherMapApiSource(
        openWeatherApi: OpenWeatherApi
    ): WeatherDataSource = OpenWeatherApiSource(openWeatherApi)
}
