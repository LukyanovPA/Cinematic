package com.pavellukyanov.cinematic.domain.popularmovie

import io.reactivex.Flowable
import io.reactivex.Single

interface PopularMovieRepo {
    fun getPopularMovie(page: Int): Flowable<List<PopularMovie>>
}