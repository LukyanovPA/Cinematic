package com.pavellukyanov.cinematic.domain.people

import com.pavellukyanov.cinematic.data.repository.people.PeopleRepo
import com.pavellukyanov.cinematic.domain.models.Movie
import io.reactivex.Single
import javax.inject.Inject

interface GetPeopleCreditsInteractor : (Int) -> Single<List<Movie>>

class GetPeopleCreditsInteractorImpl @Inject constructor(
    private val repo: PeopleRepo
) : GetPeopleCreditsInteractor {
    override fun invoke(id: Int): Single<List<Movie>> =
        repo.getPeopleCredits(id)
}