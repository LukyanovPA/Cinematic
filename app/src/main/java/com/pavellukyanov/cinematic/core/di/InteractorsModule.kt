package com.pavellukyanov.cinematic.core.di

import com.pavellukyanov.cinematic.domain.genre.GenresRepo
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractor
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractorImpl
import com.pavellukyanov.cinematic.domain.moviedetail.GetMovieDetailsInteractor
import com.pavellukyanov.cinematic.domain.moviedetail.GetMovieDetailsInteractorImpl
import com.pavellukyanov.cinematic.domain.moviedetail.MovieDetailsRepo
import com.pavellukyanov.cinematic.domain.search.SearchInteractor
import com.pavellukyanov.cinematic.domain.search.SearchInteractorImpl
import com.pavellukyanov.cinematic.domain.search.SearchRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InteractorsModule {

    @Provides
    @Singleton
    fun provideGetMovieDetailsInteractor(
        repo: MovieDetailsRepo
    ): GetMovieDetailsInteractor = GetMovieDetailsInteractorImpl(repo)

    @Provides
    @Singleton
    fun provideGetGenresInteractor(
        repo: GenresRepo
    ): GetGenresInteractor = GetGenresInteractorImpl(repo)

    @Provides
    @Singleton
    fun provideSearchInteractor(
        searchRepo: SearchRepo
    ): SearchInteractor = SearchInteractorImpl(searchRepo)
}