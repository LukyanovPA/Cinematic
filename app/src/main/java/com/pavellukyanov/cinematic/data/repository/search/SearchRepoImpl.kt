package com.pavellukyanov.cinematic.data.repository.search

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.SearchService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.configuration.toMovieList
import com.pavellukyanov.cinematic.data.repository.movie.toSearchItem
import com.pavellukyanov.cinematic.domain.search.SearchItem
import com.pavellukyanov.cinematic.domain.search.SearchRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val api: SearchService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : SearchRepo {
    override fun doSearch(query: String): Single<List<SearchItem>> {
        return Single.zip(
            api.searchMovie(query).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { searchResult, config ->
            val resultList = mutableListOf<SearchItem>()
            config.toMovieList(searchResult.results as List<MovieResponse>).forEach {
                resultList.add(it.toSearchItem())
            }
            return@zip resultList
        }
    }
}