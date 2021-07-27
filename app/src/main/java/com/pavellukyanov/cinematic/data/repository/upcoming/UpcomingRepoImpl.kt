package com.pavellukyanov.cinematic.data.repository.upcoming

import android.util.Log
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.UpcomingService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.configuration.toMovieList
import com.pavellukyanov.cinematic.data.repository.movie.insertInDatabase
import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpcomingRepoImpl @Inject constructor(
    private val api: UpcomingService,
    private val config: ConfigurationService,
    private val database: MovieDatabase
) : UpcomingRepo {
    override fun getUpcoming(page: Int): Single<List<Movie>> {
        return Single.zip(
            api.getUpcoming(page = page).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { movies, config ->
            config.toMovieList(movies.results)
        }
            .doOnSuccess { mapToUpcoming(it).insertInDatabase(database) }
            .doOnError { e ->
                when (e) {
                    is IOException -> Log.d(ERROR_LOG, e.localizedMessage!!)
                    is HttpException -> Log.d(ERROR_LOG, e.localizedMessage!!)
                    else -> throw e
                }
            }
    }

    private fun mapToUpcoming(listMovie: List<Movie>): List<Movie> {
        listMovie.forEach { movie ->
            movie.apply {
                isUpcoming = 1
            }
        }
        return listMovie
    }

    companion object {
        const val ERROR_LOG = "UpcomingRepo"
    }
}