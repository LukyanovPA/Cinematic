package com.pavellukyanov.cinematic.data.repository.people

import android.util.Log
import com.pavellukyanov.cinematic.data.api.pojo.people.PeopleResponse
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.entity.PeopleEntity
import com.pavellukyanov.cinematic.data.repository.LOG
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

fun PeopleResponse.toEntity() =
    PeopleEntity(id, biography, birthday, deathday, name, place_of_birth, profile_path)

fun PeopleResponse.toPojo() =
    PeopleDetails(id, biography, birthday, deathday, name, place_of_birth, profile_path)

fun PeopleEntity.toPeopleDetails() =
    PeopleDetails(id, biography, birthday, deathday, name, place_of_birth, profile_path)

fun PeopleDetails.toPeopleEntity() =
    PeopleEntity(id, biography, birthday, deathday, name, place_of_birth, profile_path)

fun PeopleResponse.insertInDatabase(
    database: MovieDatabase
): Completable {
    return Completable.fromAction {
        toEntity().let { people ->
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