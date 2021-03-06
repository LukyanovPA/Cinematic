package com.pavellukyanov.cinematic.data.repository.toprated

import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single

interface TopRatedRepo {
    fun getTopRated(page: Int): Single<List<Movie>>
}