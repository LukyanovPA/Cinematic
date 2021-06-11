package com.pavellukyanov.cinematic.domain.popularmovie

import com.pavellukyanov.cinematic.data.database.entity.MovieEntity

data class PopularMovie(
    val id: Int,
    var title: String,
    val posterPath: String,
    val releaseDate : String?,
    val voteAverage : Double
)

fun PopularMovie.toPopularMovieEntity() = releaseDate?.let {
    MovieEntity(
        id = id,
        originalTitle = title,
        posterPath = posterPath,
        releaseDate = it,
        voteAverage = voteAverage
    )
}