package com.pavellukyanov.cinematic.core.di

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.*
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.genres.GenresRepoImpl
import com.pavellukyanov.cinematic.data.repository.moviedetails.MovieDetailsRepoImpl
import com.pavellukyanov.cinematic.data.repository.nowplaying.NowPlayingRepoImpl
import com.pavellukyanov.cinematic.data.repository.popularmovie.PopularMovieRepoImpl
import com.pavellukyanov.cinematic.data.repository.toprated.TopRatedRepoImpl
import com.pavellukyanov.cinematic.data.repository.upcoming.UpcomingRepoImpl
import com.pavellukyanov.cinematic.domain.genre.GenresRepo
import com.pavellukyanov.cinematic.domain.moviedetail.MovieDetailsRepo
import com.pavellukyanov.cinematic.domain.nowplaying.NowPlayingRepo
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieRepo
import com.pavellukyanov.cinematic.domain.toprated.TopRatedRepo
import com.pavellukyanov.cinematic.domain.upcoming.UpcomingRepo
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

    @Provides
    fun provideTopRatedRepo(
        api: TopRatedService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): TopRatedRepo = TopRatedRepoImpl(api, config, networkMonitor, database)

    @Provides
    fun provideUpcomingRepo(
        api: UpcomingService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): UpcomingRepo = UpcomingRepoImpl(api, config, networkMonitor, database)

    @Provides
    fun provideMovieDetailsRepo(
        api: MovieDetailsService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): MovieDetailsRepo = MovieDetailsRepoImpl(api, config, networkMonitor, database)
}