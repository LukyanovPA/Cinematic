package com.pavellukyanov.cinematic.domain.popularmovie

import com.pavellukyanov.cinematic.data.database.entity.PopularMovieEntity

data class PopularMovie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate : String?,
    val voteAverage : Double
)

fun PopularMovie.toPopularMovieEntity() = releaseDate?.let {
    PopularMovieEntity(
        id = id,
        originalTitle = title,
        posterPath = posterPath,
        releaseDate = it,
        voteAverage = voteAverage
    )
}