package com.pavellukyanov.cinematic.domain.search

import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single
import javax.inject.Inject

interface SearchInteractor : (String, Int) -> Single<List<Movie>>

class SearchInteractorImpl @Inject constructor(
    private val searchRepo: SearchRepo
) : SearchInteractor {
    override fun invoke(query: String, page: Int): Single<List<Movie>> {
        return searchRepo.doSearch(query, page)
    }
}