package com.pavellukyanov.cinematic.data.repository.search

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.pojo.search.toSearchItem
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.SearchService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.domain.search.SearchItem
import com.pavellukyanov.cinematic.domain.search.SearchRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchRepoImpl(
    private val api: SearchService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : SearchRepo {
    override fun doSearch(query: String): Single<List<SearchItem>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (isAvailable) {
                    return@flatMap Single.just(emptyList())
                    // реализовать поиск по базе
                } else {
                    getSearchResult(query)
                        .doOnSuccess {
                            // писать в базу историю поиска
                        }
                }
            }
    }

    private fun getSearchResult(query: String): Single<List<SearchItem>> {
        return Single.zip(
            api.search(query = query).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { searchResult, config ->
            val resultList = mutableListOf<SearchItem>()
            searchResult.results.let { listResult ->
                listResult?.forEach { resultsItem ->
                    resultsItem?.toSearchItem(config)?.let { resultList.add(it) }
                }
            }
            return@zip resultList
        }
    }
}