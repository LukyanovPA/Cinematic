package com.pavellukyanov.cinematic.data.repository.nowplaying

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.NowPlayingService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.category.NowPlayingEntity
import com.pavellukyanov.cinematic.data.repository.configuration.toMovieList
import com.pavellukyanov.cinematic.data.repository.movie.toMovieEntity
import com.pavellukyanov.cinematic.data.repository.movie.toNowPlaying
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.nowplaying.NowPlayingRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingRepoImpl @Inject constructor(
    private val api: NowPlayingService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : NowPlayingRepo {

    override fun getNowPlayingMovies(page: Int): Single<List<Movie>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (!isAvailable) {
                    return@flatMap getNowPlayingMovieInDatabase()
                } else {
                    return@flatMap getNowPlayingInApi(page)
                        .doOnSuccess { movieList ->
                            insertMoviesInDatabase(movieList)
                        }
                }
            }
    }

    private fun getNowPlayingMovieInDatabase(): Single<List<Movie>> {
        return Single.zip(
            database.nowPlaying().getAllMovies().subscribeOn(Schedulers.io()),
            database.movies().getAllMovies().subscribeOn(Schedulers.io())
        ) { popular, movie ->
            popular.comparison(movie)
        }
    }

    private fun getNowPlayingInApi(page: Int): Single<List<Movie>> {
        return Single.zip(
            api.getNowPlaying(page = page).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { movies, config ->
            config.toMovieList(movies.results)
        }
    }

    private fun insertMoviesInDatabase(listPopularMovieResponse: List<Movie>): Completable {
        deleteTableNowPlaying()
        return Completable.fromAction {
            listPopularMovieResponse.forEach { movie ->
                movie.toMovieEntity()?.let { movieEntity ->
                    database.movies()
                        .insertMovie(movieEntity)
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                }
                insertNowPlayingInDatabase(movie.toNowPlaying())
            }
        }
    }

    private fun deleteTableNowPlaying(): Completable {
        return Completable.fromAction {
            database.nowPlaying().deleteTable()
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }

    private fun insertNowPlayingInDatabase(popularMovieEntity: NowPlayingEntity) {
        database.nowPlaying()
            .insertMovie(popularMovieEntity)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}