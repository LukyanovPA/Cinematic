package com.pavellukyanov.cinematic.domain.nowplaying

import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single

interface NowPlayingRepo {
    fun getNowPlayingMovies(page: Int): Single<List<Movie>>
}