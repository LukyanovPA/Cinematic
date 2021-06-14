package com.pavellukyanov.cinematic.data.repository.upcoming

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.UpcomingService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.category.UpcomingEntity
import com.pavellukyanov.cinematic.data.repository.configuration.toMovieList
import com.pavellukyanov.cinematic.data.repository.movie.toMovieEntity
import com.pavellukyanov.cinematic.data.repository.movie.toUpcoming
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.upcoming.UpcomingRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpcomingRepoImpl @Inject constructor(
    private val api: UpcomingService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : UpcomingRepo {
    override fun getUpcoming(page: Int): Single<List<Movie>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (!isAvailable) {
                    return@flatMap getUpcomingInDatabase()
                } else {
                    return@flatMap getUpcomingInApi(page)
                        .doOnSuccess { movieList ->
                            insertMoviesInDatabase(movieList)
                        }
                }
            }
    }

    private fun getUpcomingInDatabase(): Single<List<Movie>> {
        return Single.zip(
            database.upcoming().getAllMovies().subscribeOn(Schedulers.io()),
            database.movies().getAllMovies().subscribeOn(Schedulers.io())
        ) { popular, movie ->
            popular.comparison(movie, 1)
        }
    }

    private fun getUpcomingInApi(page: Int): Single<List<Movie>> {
        return Single.zip(
            api.getUpcoming(page = page).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { movies, config ->
            config.toMovieList(movies.results, 1)
        }
    }

    private fun insertMoviesInDatabase(listUpcomingResponse: List<Movie>): Completable {
        deleteTableTopRated()
        return Completable.fromAction {
            listUpcomingResponse.forEach { movie ->
                movie.toMovieEntity(1)?.let { movieEntity ->
                    database.movies()
                        .insertMovie(movieEntity)
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                }
                insertUpcomingInDatabase(movie.toUpcoming())
            }
        }
    }

    private fun deleteTableTopRated(): Completable {
        return Completable.fromAction {
            database.upcoming().deleteTable()
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }

    private fun insertUpcomingInDatabase(upcomingEntity: UpcomingEntity) {
        database.upcoming()
            .insertMovie(upcomingEntity)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}