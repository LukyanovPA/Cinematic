package com.pavellukyanov.cinematic.data.repository.popularmovie

import android.util.Log
import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.api.pojo.configuration.ConfigurationResponse
import com.pavellukyanov.cinematic.data.api.pojo.setupMoviePoster
import com.pavellukyanov.cinematic.data.api.pojo.toPopularMovie
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.MovieService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.data.database.entity.toPopularMovie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieRepo
import com.pavellukyanov.cinematic.domain.popularmovie.toPopularMovieEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularMovieRepoImpl @Inject constructor(
    private val api: MovieService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : PopularMovieRepo {
    override fun getPopularMovie(page: Int): Single<List<PopularMovie>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (!isAvailable) {
                    return@flatMap database.movies().getAllMovies()
                        .map { result ->
                            mappingEntityToDomain(result)
                        }
                } else {
                    return@flatMap getPopularMovieInApi(page)
                        .doOnSuccess {
                            insertPopularMoviesInDatabase(it)
                        }
                }
            }
    }

    private fun getPopularMovieInApi(page: Int): Single<List<PopularMovie>> {
        return Single.zip(
            api.getPopularMovie(page = page).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { movies, config ->
            mappingPojoToDomain(movies.results, config)
        }
    }

    private fun mappingPojoToDomain(
        listPopularMovieResponse: List<MovieResponse>,
        config: ConfigurationResponse
    ): List<PopularMovie> {
        val mappingList = mutableListOf<PopularMovie>()
        listPopularMovieResponse.forEach {
            Log.d("ttt", it.title)
            it.setupMoviePoster(
                config.images.posterSizes,
                config.images.baseUrl
            )
            mappingList.add(it.toPopularMovie())
        }
        return mappingList
    }

    private fun mappingEntityToDomain(listMovieResponse: List<MovieEntity>): List<PopularMovie> {
        val mappingList = mutableListOf<PopularMovie>()
        listMovieResponse.forEach {
            mappingList.add(it.toPopularMovie())
        }
        return mappingList
    }

    private fun insertPopularMoviesInDatabase(listPopularMovieResponse: List<PopularMovie>) {
        listPopularMovieResponse.forEach {
            it.toPopularMovieEntity()?.let { it1 ->
                database.movies()
                    .insertMovie(it1)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }
        }
    }
}