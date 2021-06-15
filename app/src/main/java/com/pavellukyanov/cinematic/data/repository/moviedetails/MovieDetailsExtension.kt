package com.pavellukyanov.cinematic.data.repository.moviedetails

import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.MovieDetailsResponse
import com.pavellukyanov.cinematic.domain.models.MovieDetails
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes

fun MovieDetailsResponse.setupMoviePoster(posterSizes: List<String>, baseUrl: String) {
    PosterSizeList.posterSizes = posterSizes
    moviePoster = "$baseUrl/${PosterSizes.ORIGINAL.size}/$backdropPath"
}

fun MovieDetailsResponse.toMovieDetails() = MovieDetails(
    id = id,
    title = title,
    posterPath = moviePoster,
    voteAverage = voteAverage,
    releaseDate = releaseDate
)