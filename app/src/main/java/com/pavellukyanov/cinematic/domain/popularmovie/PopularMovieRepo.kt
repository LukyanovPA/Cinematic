package com.pavellukyanov.cinematic.domain.popularmovie

import io.reactivex.Single

interface PopularMovieRepo {
    fun getPopularMovie(page: Int): Single<List<PopularMovie>>
}