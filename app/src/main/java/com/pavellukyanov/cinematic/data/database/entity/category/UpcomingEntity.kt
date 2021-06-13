package com.pavellukyanov.cinematic.data.database.entity.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming")
class UpcomingEntity(
    @PrimaryKey val movieId: Int
)