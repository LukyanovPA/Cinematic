package com.pavellukyanov.cinematic.data.repository.people

import com.pavellukyanov.cinematic.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.cinematic.data.api.services.ConfigurationService
import com.pavellukyanov.cinematic.data.api.services.PeopleService
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.repository.configuration.toCreditsMovie
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PeopleRepoImpl @Inject constructor(
    private val api: PeopleService,
    private val config: ConfigurationService,
    private val networkMonitor: NetworkMonitor,
    private val database: MovieDatabase
) : PeopleRepo {
    override fun getPeopleDetails(id: Int): Single<PeopleDetails> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { isAvailable ->
                if (!isAvailable) {
                    return@flatMap database.people().getPeople(id)
                        .map { people ->
                            people.toPeopleDetails()
                        }
                } else {
                    return@flatMap getPeopleInApi(id)
                }
            }
    }

    private fun getPeopleInApi(id: Int): Single<PeopleDetails> {
        return Single.zip(
            api.getPeopleDetails(id = id).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { peopleResponse, config ->
            peopleResponse.setupPoster(config)
            peopleResponse.toDomain()
        }
            .doOnSuccess { it.insertInDatabase(database) }
    }

    override fun getPeopleCredits(id: Int): Single<List<Movie>> {
        return getPeopleCreditsInApi(id)
    }

    private fun getPeopleCreditsInApi(id: Int): Single<List<Movie>> {
        return Single.zip(
            api.getPeopleCredits(id = id).subscribeOn(Schedulers.io()),
            config.getConfiguration().subscribeOn(Schedulers.io())
        ) { peopleCreditsResponse, config ->
            config.toCreditsMovie(peopleCreditsResponse.cast)
        }
    }
}