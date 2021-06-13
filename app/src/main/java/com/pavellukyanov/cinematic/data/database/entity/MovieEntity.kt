package com.pavellukyanov.cinematic.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pavellukyanov.cinematic.data.database.entity.category.PopularMovieEntity
import com.pavellukyanov.cinematic.domain.models.Movie

@Entity(tableName = "movie")
class MovieEntity(
    @PrimaryKey val id: Int,
    val originalTitle: String,
    val posterPath: String,
    var releaseDate: String,
    var voteAverage: Double
)