package com.pavellukyanov.cinematic.domain.models

import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.NowPlayingEntity
import com.pavellukyanov.cinematic.data.database.entity.category.PopularMovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.TopRatedEntity
import com.pavellukyanov.cinematic.data.database.entity.category.UpcomingEntity

data class Movie(
    val id: Int,
    var title: String,
    val posterPath: String,
    val releaseDate: String?,
    val voteAverage: Double
)

fun Movie.toMovieEntity() = releaseDate?.let {
    MovieEntity(
        id = id,
        originalTitle = title,
        posterPath = posterPath,
        releaseDate = it,
        voteAverage = voteAverage
    )
}

fun Movie.toNowPlaying() = NowPlayingEntity(movieId = id)
fun Movie.toUpcoming() = UpcomingEntity(movieId = id)
fun Movie.toTopRated() = TopRatedEntity(movieId = id)