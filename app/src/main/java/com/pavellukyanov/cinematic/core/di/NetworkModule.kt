package com.pavellukyanov.cinematic.core.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pavellukyanov.cinematic.BuildConfig
import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitorImpl
import com.pavellukyanov.cinematic.data.api.services.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY_VALUE = "8a6444939a974846cb13a2ec5853c60a"
    private const val API_KEY = "api_key"

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
        }

        okHttpBuilder.addInterceptor {
            val request = it.request()
            val url = request.url
                .newBuilder()
                .addQueryParameter(API_KEY, API_KEY_VALUE)
                .build()
            it.proceed(request.newBuilder().url(url).build())
        }

        val contentType = "application/json".toMediaType()
        val json = Json {
            coerceInputValues = true
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor =
        NetworkMonitorImpl(context)

    //Provides Api Services

    @Provides
    @Singleton
    fun providePopularMovieApiService(retrofit: Retrofit): PopularMovieService =
        retrofit.create(PopularMovieService::class.java)

    @Provides
    @Singleton
    fun provideConfigurationService(retrofit: Retrofit): ConfigurationService =
        retrofit.create(ConfigurationService::class.java)

    @Provides
    @Singleton
    fun provideGenresService(retrofit: Retrofit): GenresService =
        retrofit.create(GenresService::class.java)

    @Provides
    @Singleton
    fun provideNowPlayingService(retrofit: Retrofit): NowPlayingService =
        retrofit.create(NowPlayingService::class.java)

    @Provides
    @Singleton
    fun provideTopRatedService(retrofit: Retrofit): TopRatedService =
        retrofit.create(TopRatedService::class.java)

    @Provides
    @Singleton
    fun provideUpcomingService(retrofit: Retrofit): UpcomingService =
        retrofit.create(UpcomingService::class.java)

    @Provides
    @Singleton
    fun provideMovieDetailsService(retrofit: Retrofit): MovieDetailsService =
        retrofit.create(MovieDetailsService::class.java)
}