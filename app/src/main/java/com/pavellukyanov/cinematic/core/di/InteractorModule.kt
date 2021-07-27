package com.pavellukyanov.cinematic.core.di

import com.pavellukyanov.cinematic.domain.genre.GenresRepo
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractor
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractorImpl
import com.pavellukyanov.cinematic.domain.moviedetail.GetMovieDetailsInteractor
import com.pavellukyanov.cinematic.domain.moviedetail.GetMovieDetailsInteractorImpl
import com.pavellukyanov.cinematic.domain.moviedetail.MovieDetailsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object InteractorModule {

    @Provides
    fun provideGetGenresInteractor(
        genresRepo: GenresRepo
    ): GetGenresInteractor = GetGenresInteractorImpl(genresRepo)

    @Provides
    fun provideGetMovieDetailsInteractor(
        movieDetailsRepo: MovieDetailsRepo
    ): GetMovieDetailsInteractor = GetMovieDetailsInteractorImpl(movieDetailsRepo)
}