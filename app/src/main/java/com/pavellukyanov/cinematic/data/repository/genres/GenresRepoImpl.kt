package com.pavellukyanov.cinematic.data.repository.genres

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.GenresService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.domain.genre.GenresRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenresRepoImpl @Inject constructor(
    private val api: GenresService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : GenresRepo {

    override fun getGenres(): Single<List<Genre>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (!isAvailable) {
                    return@flatMap database.genres().getAllGenres()
                        .map { result ->
                            result.toListGenre()
                        }
                } else {
                    return@flatMap getGenresInApi()
                        .doOnSuccess {
                            insertGenresInDatabase(it)
                        }
                }
            }
    }

    private fun getGenresInApi(): Single<List<Genre>> {
        return api.getGenres()
            .subscribeOn(Schedulers.io())
            .map { genresResponse ->
                genresResponse.toListGenre()
            }
    }

    private fun insertGenresInDatabase(listGenresResponse: List<Genre>): Completable {
        return Completable.fromAction {
            listGenresResponse.forEach {
                database.genres()
                    .insertGenre(it.toGenreEntity())
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }
        }
    }
}