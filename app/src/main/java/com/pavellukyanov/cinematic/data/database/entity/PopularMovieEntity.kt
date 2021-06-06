package com.pavellukyanov.cinematic.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie

@Entity(tableName = "popular_movie")
class PopularMovieEntity(
    @PrimaryKey val id: Int,
    val originalTitle: String,
    val posterPath: String,
    var releaseDate: String,
    var voteAverage: Double
)

fun PopularMovieEntity.toPopularMovie() = PopularMovie(
    id = id,
    originalTitle = originalTitle,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)