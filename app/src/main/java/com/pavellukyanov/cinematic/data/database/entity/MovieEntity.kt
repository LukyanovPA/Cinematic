package com.pavellukyanov.cinematic.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val posterPath: String,
    var releaseDate: String,
    var voteAverage: Double,
    val isUpcoming: Int = 0
)