package com.pavellukyanov.cinematic.domain.search

import io.reactivex.Single

interface SearchRepo {
    fun doSearch(query: String): Single<List<SearchItem>>
}