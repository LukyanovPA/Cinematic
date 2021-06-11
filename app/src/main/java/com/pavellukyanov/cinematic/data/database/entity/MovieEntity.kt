package com.pavellukyanov.cinematic.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie

@Entity(tableName = "movie")
class MovieEntity(
    @PrimaryKey val id: Int,
    val originalTitle: String,
    val posterPath: String,
    var releaseDate: String,
    var voteAverage: Double
)

fun MovieEntity.toPopularMovie() = PopularMovie(
    id = id,
    title = originalTitle,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)