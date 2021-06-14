package com.pavellukyanov.cinematic.data.database.entity.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_rated")
class TopRatedEntity(
    @PrimaryKey val movieId: Int
)