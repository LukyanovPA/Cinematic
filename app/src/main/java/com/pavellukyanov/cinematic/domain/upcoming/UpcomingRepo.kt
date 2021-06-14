package com.pavellukyanov.cinematic.domain.upcoming

import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single

interface UpcomingRepo {
    fun getUpcoming(page: Int): Single<List<Movie>>
}