package com.pavellukyanov.cinematic.data.repository.search

import android.util.Log
import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.SearchService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.configuration.toMovieList
import com.pavellukyanov.cinematic.data.repository.insertInDatabase
import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val api: SearchService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : SearchRepo {
    override fun doSearch(query: String, page: Int): Single<List<Movie>> {
        return Single.zip(
            api.searchMovie(query = query, page = page).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { searchResult, config ->
            searchResult.results.let { config.toMovieList(it as List<MovieResponse>) }
        }
            .doOnSuccess { it.insertInDatabase(database) }
            .doOnError { e ->
                when (e) {
                    is IOException -> Log.d(ERROR_LOG, e.localizedMessage!!)
                    is HttpException -> Log.d(ERROR_LOG, e.localizedMessage!!)
                    else -> throw e
                }
            }
    }

    companion object {
        const val ERROR_LOG = "SearchRepo"
    }
}