package com.pavellukyanov.cinematic.domain.search

import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single

interface SearchRepo {
    fun doSearch(query: String): Single<List<Movie>>
}