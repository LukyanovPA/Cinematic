package com.pavellukyanov.cinematic.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pavellukyanov.cinematic.domain.genre.Genre

@Entity(tableName = "details")
class DetailsEntity(
    @PrimaryKey val id: Int,
    val title : String,
    val originalTitle: String,
    val posterPath : String,
    val voteAverage : Double,
    val releaseDate : String,
    val overview : String,
    val genres: List<Genre>
)