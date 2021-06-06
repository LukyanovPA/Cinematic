package com.pavellukyanov.cinematic.data.repository

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.MovieService
import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.api.pojo.toPopularMovie
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.PopularMovieEntity
import com.pavellukyanov.cinematic.data.database.entity.toPopularMovie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieRepo
import com.pavellukyanov.cinematic.domain.popularmovie.toPopularMovieEntity
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularMovieRepoImpl @Inject constructor(
    private val api: MovieService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : PopularMovieRepo {
    override fun getPopularMovie(page: Int): Flowable<List<PopularMovie>> {
        return Flowable.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (!isAvailable) {
                    return@flatMap database.popularMovie().getAllMovies()
                        .map { result ->
                            mappingEntityToDomain(result)
                        }
                } else {
                    return@flatMap getPopularMovieInApi(page)
                        .doOnNext {
                            insertPopularMoviesInDatabase(it)
                        }
                }
            }
    }

    private fun getPopularMovieInApi(page: Int): Flowable<List<PopularMovie>> {
        return api.getPopularMovie(page = page)
            .subscribeOn(Schedulers.io())
            .map { mappingPojoToDomain(it.results) }
    }

    private fun mappingPojoToDomain(listPopularMovieResponse: List<MovieResponse>): List<PopularMovie> {
        val mappingList = mutableListOf<PopularMovie>()
        listPopularMovieResponse.forEach {
            mappingList.add(it.toPopularMovie())
        }
        return mappingList
    }

    private fun mappingEntityToDomain(listPopularMovieResponse: List<PopularMovieEntity>): List<PopularMovie> {
        val mappingList = mutableListOf<PopularMovie>()
        listPopularMovieResponse.forEach {
            mappingList.add(it.toPopularMovie())
        }
        return mappingList
    }

    private fun insertPopularMoviesInDatabase(listPopularMovieResponse: List<PopularMovie>) {
        listPopularMovieResponse.forEach {
            database.popularMovie()
                .insertMovie(it.toPopularMovieEntity())
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }
}