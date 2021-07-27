package com.pavellukyanov.cinematic.domain.search

import io.reactivex.Single
import javax.inject.Inject

interface SearchInteractor : (String) -> Single<List<SearchItem>>

class SearchInteractorImpl @Inject constructor(
    private val searchRepo: SearchRepo
) : SearchInteractor {
    override fun invoke(query: String): Single<List<SearchItem>> =
        searchRepo.doSearch(query)
}