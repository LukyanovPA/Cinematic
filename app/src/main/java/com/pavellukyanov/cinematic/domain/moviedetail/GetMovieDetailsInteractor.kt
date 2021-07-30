package com.pavellukyanov.cinematic.domain.moviedetail

import com.pavellukyanov.cinematic.data.repository.moviedetails.MovieDetailsRepo
import com.pavellukyanov.cinematic.domain.models.MovieDetails
import io.reactivex.Single
import javax.inject.Inject

interface GetMovieDetailsInteractor : (Int) -> Single<MovieDetails>

class GetMovieDetailsInteractorImpl @Inject constructor(
    private val repo: MovieDetailsRepo
) : GetMovieDetailsInteractor {
    override fun invoke(movieId: Int): Single<MovieDetails> =
        repo.getMovieDetails(movieId)
}