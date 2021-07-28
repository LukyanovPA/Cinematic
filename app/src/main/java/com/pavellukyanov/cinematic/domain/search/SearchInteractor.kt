package com.pavellukyanov.cinematic.domain.search

import android.util.Log
import io.reactivex.Single
import javax.inject.Inject

interface SearchInteractor : (String) -> Single<List<SearchItem>>

class SearchInteractorImpl @Inject constructor(
    private val searchRepo: SearchRepo
) : SearchInteractor {
    override fun invoke(query: String): Single<List<SearchItem>> {
        Log.d("ttt", "interactor - $query")
        return searchRepo.doSearch(query)
    }
}