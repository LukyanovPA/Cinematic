package com.pavellukyanov.cinematic.data.repository.movie

import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.PopularMovieEntity
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes

fun MovieResponse.setupMoviePoster(posterSizes: List<String>, baseUrl: String) {
    PosterSizeList.posterSizes = posterSizes
    moviePoster = "$baseUrl/${PosterSizes.W500.size}/$posterPath"
}

fun MovieResponse.toMovie() = Movie(
    id = id,
    title = title,
    posterPath = moviePoster,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun MovieEntity.toMovie() = Movie(
    id = id,
    title = originalTitle,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun Movie.toPopularMovie() = PopularMovieEntity(movieId = id)