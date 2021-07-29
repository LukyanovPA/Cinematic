package com.pavellukyanov.cinematic.data.repository.toprated

import android.util.Log
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.TopRatedService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.configuration.toMovieList
import com.pavellukyanov.cinematic.data.repository.insertInDatabase
import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopRatedRepoImpl @Inject constructor(
    private val api: TopRatedService,
    private val config: ConfigurationService,
    private val database: MovieDatabase
) : TopRatedRepo {

    override fun getTopRated(page: Int): Single<List<Movie>> {
        return Single.zip(
            api.getTopRatedMovies(page = page).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { movies, config ->
            config.toMovieList(movies.results)
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
        const val ERROR_LOG = "TopRatedRepo"
    }
}