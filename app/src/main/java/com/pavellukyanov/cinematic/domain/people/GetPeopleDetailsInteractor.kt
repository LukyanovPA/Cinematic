package com.pavellukyanov.cinematic.domain.people

import com.pavellukyanov.cinematic.data.repository.people.PeopleRepo
import com.pavellukyanov.cinematic.domain.models.PeopleDetails
import io.reactivex.Single
import javax.inject.Inject

interface GetPeopleDetailsInteractor : (Int) -> Single<PeopleDetails>

class GetPeopleDetailsInteractorImpl @Inject constructor(
    private val repo: PeopleRepo
) : GetPeopleDetailsInteractor {
    override fun invoke(id: Int): Single<PeopleDetails> =
        repo.getPeopleDetails(id)
}