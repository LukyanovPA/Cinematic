package com.pavellukyanov.cinematic.core.di

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.GenresService
import com.pavellukyanov.cinematic.data.api.services.NowPlayingService
import com.pavellukyanov.cinematic.data.api.services.PopularMovieService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.genres.GenresRepoImpl
import com.pavellukyanov.cinematic.data.repository.nowplaying.NowPlayingRepoImpl
import com.pavellukyanov.cinematic.data.repository.popularmovie.PopularMovieRepoImpl
import com.pavellukyanov.cinematic.domain.genre.GenresRepo
import com.pavellukyanov.cinematic.domain.nowplaying.NowPlayingRepo
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
        api: PopularMovieService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): PopularMovieRepo = PopularMovieRepoImpl(api, config, networkMonitor, database)

    @Provides
    fun provideGenresRepo(
        api: GenresService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): GenresRepo = GenresRepoImpl(api, networkMonitor, database)

    @Provides
    fun provideNowPlayingRepo(
        api: NowPlayingService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): NowPlayingRepo = NowPlayingRepoImpl(api, config, networkMonitor, database)
}