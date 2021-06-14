package com.pavellukyanov.cinematic.data.repository.toprated

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.TopRatedService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.category.TopRatedEntity
import com.pavellukyanov.cinematic.data.repository.configuration.toMovieList
import com.pavellukyanov.cinematic.data.repository.movie.toMovieEntity
import com.pavellukyanov.cinematic.data.repository.movie.toTopRated
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.toprated.TopRatedRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopRatedRepoImpl @Inject constructor(
    private val api: TopRatedService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : TopRatedRepo {

    override fun getTopRated(page: Int): Single<List<Movie>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (!isAvailable) {
                    return@flatMap getTopRatedInDatabase()
                } else {
                    return@flatMap getTopRatedInApi(page)
                        .doOnSuccess { movieList ->
                            insertMoviesInDatabase(movieList)
                        }
                }
            }
    }

    private fun getTopRatedInDatabase(): Single<List<Movie>> {
        return Single.zip(
            database.topRated().getAllMovies().subscribeOn(Schedulers.io()),
            database.movies().getAllMovies().subscribeOn(Schedulers.io())
        ) { popular, movie ->
            popular.comparison(movie, 0)
        }
    }

    private fun getTopRatedInApi(page: Int): Single<List<Movie>> {
        return Single.zip(
            api.getTopRatedMovies(page = page).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { movies, config ->
            config.toMovieList(movies.results, 0)
        }
    }

    private fun insertMoviesInDatabase(listTopRatedResponse: List<Movie>): Completable {
        deleteTableTopRated()
        return Completable.fromAction {
            listTopRatedResponse.forEach { movie ->
                movie.toMovieEntity(0)?.let { movieEntity ->
                    database.movies()
                        .insertMovie(movieEntity)
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                }
                insertTopRatedInDatabase(movie.toTopRated())
            }
        }
    }

    private fun deleteTableTopRated(): Completable {
        return Completable.fromAction {
            database.topRated().deleteTable()
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }

    private fun insertTopRatedInDatabase(topRatedEntity: TopRatedEntity) {
        database.topRated()
            .insertMovie(topRatedEntity)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}