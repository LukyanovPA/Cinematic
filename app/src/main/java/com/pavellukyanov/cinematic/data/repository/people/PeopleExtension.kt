package com.pavellukyanov.cinematic.data.repository.people

import android.util.Log
import com.pavellukyanov.cinematic.data.api.pojo.configuration.ConfigurationResponse
import com.pavellukyanov.cinematic.data.api.pojo.people.PeopleResponse
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.PeopleEntity
import com.pavellukyanov.cinematic.data.repository.LOG
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import com.pavellukyanov.cinematic.utils.DateHelper
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

fun PeopleResponse.setupPoster(config: ConfigurationResponse) {
    PosterSizeList.posterSizes = config.images.posterSizes
    poster = "${config.images.baseUrl}/${PosterSizes.W500.size}/$profile_path"
}

fun PeopleResponse.toEntity() =
    PeopleEntity(
        id,
        biography,
        birthday,
        deathday,
        DateHelper.getAge(birthday, deathday).toString(),
        name,
        place_of_birth,
        poster
    )

fun PeopleResponse.toDomain() =
    PeopleDetails(
        id,
        biography,
        birthday,
        deathday,
        DateHelper.getAge(birthday, deathday).toString(),
        name,
        place_of_birth,
        poster
    )

fun PeopleEntity.toPeopleDetails() =
    PeopleDetails(
        id = id,
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        age = age,
        name = name,
        place_of_birth = place_of_birth,
        profile_path = profile_path
    )

fun PeopleDetails.toPeopleEntity() =
    PeopleEntity(
        id = id,
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        age = age,
        name = name,
        place_of_birth = place_of_birth,
        profile_path = profile_path
    )

fun PeopleDetails.insertInDatabase(
    database: MovieDatabase
): Completable {
    return Completable.fromAction {
        toPeopleEntity().let { people ->
            database.people()
                .insertPeople(people)
                .subscribeOn(Schedulers.io())
                .doOnError { e ->
                    when (e) {
                        is IOException -> Log.d(LOG, e.localizedMessage!!)
                        is HttpException -> Log.d(LOG, e.localizedMessage!!)
                        else -> throw e
                    }
                }
                .subscribe()
        }
    }
}