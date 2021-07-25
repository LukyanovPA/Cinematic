package com.pavellukyanov.cinematic.domain.moviedetail

import com.pavellukyanov.cinematic.domain.models.MovieDetails
import io.reactivex.Single

interface MovieDetailsRepo {
    fun getMovieDetails(movieId: Int): Single<MovieDetails>
}