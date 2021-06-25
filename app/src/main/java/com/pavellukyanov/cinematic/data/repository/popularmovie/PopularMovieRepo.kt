package com.pavellukyanov.cinematic.data.repository.popularmovie


import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single

interface PopularMovieRepo {
    fun getPopularMovie(page: Int): Single<List<Movie>>
}