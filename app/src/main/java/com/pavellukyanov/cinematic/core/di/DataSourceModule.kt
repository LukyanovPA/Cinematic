package com.pavellukyanov.cinematic.core.di

import com.pavellukyanov.cinematic.data.repository.nowplaying.NowPlayingRepo
import com.pavellukyanov.cinematic.data.repository.popularmovie.PopularMovieRepo
import com.pavellukyanov.cinematic.data.repository.toprated.TopRatedRepo
import com.pavellukyanov.cinematic.data.repository.upcoming.UpcomingRepo
import com.pavellukyanov.cinematic.domain.nowplaying.NowPlayingDataSource
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieDataSource
import com.pavellukyanov.cinematic.domain.search.SearchInteractor
import com.pavellukyanov.cinematic.domain.search.SearchMovieDataSource
import com.pavellukyanov.cinematic.domain.toprated.TopRatedDataSource
import com.pavellukyanov.cinematic.domain.upcoming.UpcomingDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    fun provideNowPlayingDataSource(
        nowPlayingRepo: NowPlayingRepo
    ) = NowPlayingDataSource(nowPlayingRepo)

    @Provides
    fun providePopularMovieDataSource(
        popularMovieRepo: PopularMovieRepo
    ) = PopularMovieDataSource(popularMovieRepo)

    @Provides
    fun provideTopRatedDataSource(
        topRatedRepo: TopRatedRepo
    ) = TopRatedDataSource(topRatedRepo)

    @Provides
    fun provideUpcomingDataSource(
        upcomingRepo: UpcomingRepo
    ) = UpcomingDataSource(upcomingRepo)

    @Provides
    fun provideSearchMovieDataSource(
        searchRepo: SearchInteractor
    ) = SearchMovieDataSource(searchRepo)
}