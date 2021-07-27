package com.pavellukyanov.cinematic.domain.moviedetail

import com.pavellukyanov.cinematic.domain.models.MovieDetails
import io.reactivex.Single
import javax.inject.Inject

interface GetMovieDetailsInteractor : (Int) -> Single<MovieDetails>

class GetMovieDetailsInteractorImpl @Inject constructor(
    private val movieDetailsRepo: MovieDetailsRepo
) : GetMovieDetailsInteractor {
    override fun invoke(movieId: Int): Single<MovieDetails> {
        return movieDetailsRepo.getMovieDetails(movieId)
            .map { it }
    }
}