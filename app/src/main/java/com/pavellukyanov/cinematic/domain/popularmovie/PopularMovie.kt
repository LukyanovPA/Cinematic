package com.pavellukyanov.cinematic.domain.popularmovie

import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.database.entity.PopularMovieEntity

class PopularMovie(
    val id: Int,
    val originalTitle: String,
    val posterPath: String,
    var releaseDate : String,
    var voteAverage : Double
)

fun PopularMovie.toPopularMovieEntity() = PopularMovieEntity(
    id = id,
    originalTitle = originalTitle,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)