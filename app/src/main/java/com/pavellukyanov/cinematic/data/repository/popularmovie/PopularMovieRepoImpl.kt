package com.pavellukyanov.cinematic.data.repository.popularmovie

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.PopularMovieService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.category.PopularMovieEntity
import com.pavellukyanov.cinematic.data.repository.configuration.toMovieList
import com.pavellukyanov.cinematic.data.repository.movie.toMovieEntity
import com.pavellukyanov.cinematic.data.repository.movie.toPopularMovie
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularMovieRepoImpl @Inject constructor(
    private val api: PopularMovieService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : PopularMovieRepo {

    override fun getPopularMovie(page: Int): Single<List<Movie>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (!isAvailable) {
                    return@flatMap getPopularMovieInDatabase()
                } else {
                    return@flatMap getPopularMovieInApi(page)
                        .doOnSuccess { movieList ->
                            insertMoviesInDatabase(movieList)
                        }
                }
            }
    }

    private fun getPopularMovieInDatabase(): Single<List<Movie>> {
        return Single.zip(
            database.popularMovie().getAllMovies().subscribeOn(Schedulers.io()),
            database.movies().getAllMovies().subscribeOn(Schedulers.io())
        ) { popular, movie ->
            popular.comparison(movie, 0)
        }
    }

    private fun getPopularMovieInApi(page: Int): Single<List<Movie>> {
        return Single.zip(
            api.getPopularMovie(page = page).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { movies, config ->
            config.toMovieList(movies.results, 0)
        }
    }

    private fun insertMoviesInDatabase(listPopularMovieResponse: List<Movie>): Completable {
        deleteTablePopularMovie()
        return Completable.fromAction {
            listPopularMovieResponse.forEach { movie ->
                movie.toMovieEntity(0)?.let { movieEntity ->
                    database.movies()
                        .insertMovie(movieEntity)
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                }
                insertPopularMovieInDatabase(movie.toPopularMovie())
            }
        }
    }

    private fun deleteTablePopularMovie(): Completable {
        return Completable.fromAction {
            database.popularMovie().deleteTable()
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }

    private fun insertPopularMovieInDatabase(popularMovieEntity: PopularMovieEntity) {
        database.popularMovie()
            .insertMovie(popularMovieEntity)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}