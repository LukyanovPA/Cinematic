package com.pavellukyanov.cinematic.data.repository

import com.pavellukyanov.cinematic.data.api.MovieService
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieRepo
import io.reactivex.Single
import javax.inject.Inject

class PopularMovieRepoImpl @Inject constructor(
    private val api: MovieService
) : PopularMovieRepo {
    override fun getPopularMovie(): Single<List<PopularMovie>> {
        return api.getPopularMovie()
            .flatMap { it.results }
    }
}