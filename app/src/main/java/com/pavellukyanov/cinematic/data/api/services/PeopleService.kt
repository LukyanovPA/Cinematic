package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.people.PeopleResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {

    @GET("person/{person_id}")
    fun getPeopleDetails(
        @Path("person_id") id: Int,
        @Query("language") language: String = RequestParameters.LANGUAGE_RU
    ): Single<PeopleResponse>
}