package com.pavellukyanov.cinematic.domain.genre

import io.reactivex.Single
import javax.inject.Inject

interface GetGenresInteractor : () -> Single<List<Genre>>

class GetGenresInteractorImpl @Inject constructor(
    private val genresRepo: GenresRepo
) : GetGenresInteractor {
    override fun invoke(): Single<List<Genre>> = genresRepo.getGenres()
        .map { it }
}