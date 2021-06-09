package com.pavellukyanov.cinematic.core.di

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.MovieService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.popularmovie.PopularMovieRepoImpl
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Provides
    fun providePopularMovieRepo(
        api: MovieService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): PopularMovieRepo = PopularMovieRepoImpl(api, config, networkMonitor, database)
}