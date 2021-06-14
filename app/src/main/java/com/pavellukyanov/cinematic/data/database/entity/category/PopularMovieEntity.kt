package com.pavellukyanov.cinematic.data.database.entity.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movie")
class PopularMovieEntity(
    @PrimaryKey val movieId: Int
)