package com.pavellukyanov.cinematic.data.repository.moviedetails

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.MovieDetailsService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.configuration.toDetails
import com.pavellukyanov.cinematic.data.repository.configuration.toListActor
import com.pavellukyanov.cinematic.data.repository.configuration.toListCrew
import com.pavellukyanov.cinematic.domain.models.MovieDetails
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsRepoImpl @Inject constructor(
    private val api: MovieDetailsService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : MovieDetailsRepo {
    override fun getMovieDetails(movieId: Int): Single<MovieDetails> {
        //временно
        return Single.just(api.getMovieId(movieId))
            .flatMap { getMovieDetailsInApi(movieId) }
    }

    private fun getMovieDetailsInApi(movieId: Int): Single<MovieDetails> {
        return Single.zip(
            api.getMovieId(movieId = movieId).subscribeOn(Schedulers.io()),
            api.getCredits(movieId = movieId).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { movie, credits, config ->
            MovieDetailsMapperImpl().invoke(
                config.toDetails(movie),
                config.toListActor(credits),
                config.toListCrew(credits)
            )
        }
    }
}