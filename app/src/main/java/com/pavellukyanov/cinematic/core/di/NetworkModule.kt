package com.pavellukyanov.cinematic.core.di

import android.content.Context
import com.pavellukyanov.cinematic.BuildConfig
import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitorImpl
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY_VALUE = "8a6444939a974846cb13a2ec5853c60a"
    private const val API_KEY = "api_key"

    @JvmStatic
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

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor = NetworkMonitorImpl(context)

    //Provides Api Services

    @JvmStatic
    @Provides
    @Singleton
    fun providePopularMovieApiService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @JvmStatic
    @Provides
    @Singleton
    fun provideConfigurationService(retrofit: Retrofit): ConfigurationService =
        retrofit.create(ConfigurationService::class.java)
}