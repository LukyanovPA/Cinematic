package com.pavellukyanov.cinematic.core.di

import com.pavellukyanov.cinematic.data.repository.genres.GenresRepo
import com.pavellukyanov.cinematic.data.repository.moviedetails.MovieDetailsRepo
import com.pavellukyanov.cinematic.data.repository.people.PeopleRepo
import com.pavellukyanov.cinematic.data.repository.search.SearchRepo
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractor
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractorImpl
import com.pavellukyanov.cinematic.domain.moviedetail.GetMovieDetailsInteractor
import com.pavellukyanov.cinematic.domain.moviedetail.GetMovieDetailsInteractorImpl
import com.pavellukyanov.cinematic.domain.people.GetPeopleDetailsInteractor
import com.pavellukyanov.cinematic.domain.people.GetPeopleDetailsInteractorImpl
import com.pavellukyanov.cinematic.domain.search.SearchInteractor
import com.pavellukyanov.cinematic.domain.search.SearchInteractorImpl
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
        repo: SearchRepo
    ): SearchInteractor = SearchInteractorImpl(repo)

    @Provides
    @Singleton
    fun provideGetPeopleDetailsInteractor(
        repo: PeopleRepo
    ): GetPeopleDetailsInteractor = GetPeopleDetailsInteractorImpl(repo)
}