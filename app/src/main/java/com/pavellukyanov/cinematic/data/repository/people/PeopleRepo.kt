package com.pavellukyanov.cinematic.data.repository.people

import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import io.reactivex.Single

interface PeopleRepo {

    fun getPeopleDetails(id: Int): Single<PeopleDetails>

    fun getPeopleCredits(id: Int): Single<List<Movie>>
}