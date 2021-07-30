package com.pavellukyanov.cinematic.core.di

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.*
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.genres.GenresRepo
import com.pavellukyanov.cinematic.data.repository.genres.GenresRepoImpl
import com.pavellukyanov.cinematic.data.repository.moviedetails.MovieDetailsRepo
import com.pavellukyanov.cinematic.data.repository.moviedetails.MovieDetailsRepoImpl
import com.pavellukyanov.cinematic.data.repository.nowplaying.NowPlayingRepo
import com.pavellukyanov.cinematic.data.repository.nowplaying.NowPlayingRepoImpl
import com.pavellukyanov.cinematic.data.repository.people.PeopleRepo
import com.pavellukyanov.cinematic.data.repository.people.PeopleRepoImpl
import com.pavellukyanov.cinematic.data.repository.popularmovie.PopularMovieRepo
import com.pavellukyanov.cinematic.data.repository.popularmovie.PopularMovieRepoImpl
import com.pavellukyanov.cinematic.data.repository.search.SearchRepo
import com.pavellukyanov.cinematic.data.repository.search.SearchRepoImpl
import com.pavellukyanov.cinematic.data.repository.toprated.TopRatedRepo
import com.pavellukyanov.cinematic.data.repository.toprated.TopRatedRepoImpl
import com.pavellukyanov.cinematic.data.repository.upcoming.UpcomingRepo
import com.pavellukyanov.cinematic.data.repository.upcoming.UpcomingRepoImpl
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
        database: MovieDatabase
    ): PopularMovieRepo = PopularMovieRepoImpl(api, config, database)

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
        database: MovieDatabase
    ): NowPlayingRepo = NowPlayingRepoImpl(api, config, database)

    @Provides
    fun provideTopRatedRepo(
        api: TopRatedService,
        config: ConfigurationService,
        database: MovieDatabase
    ): TopRatedRepo = TopRatedRepoImpl(api, config, database)

    @Provides
    fun provideUpcomingRepo(
        api: UpcomingService,
        config: ConfigurationService,
        database: MovieDatabase
    ): UpcomingRepo = UpcomingRepoImpl(api, config, database)

    @Provides
    fun provideMovieDetailsRepo(
        api: MovieDetailsService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): MovieDetailsRepo = MovieDetailsRepoImpl(api, config, networkMonitor, database)

    @Provides
    fun provideSearchRepo(
        api: SearchService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): SearchRepo = SearchRepoImpl(api, config, networkMonitor, database)

    @Provides
    fun providePeopleRepo(
        api: PeopleService,
        config: ConfigurationService,
        networkMonitor: NetworkMonitor,
        database: MovieDatabase
    ): PeopleRepo = PeopleRepoImpl(api, config, networkMonitor, database)
}