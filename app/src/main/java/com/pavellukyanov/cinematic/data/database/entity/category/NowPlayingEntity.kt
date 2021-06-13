package com.pavellukyanov.cinematic.data.database.entity.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing")
class NowPlayingEntity(
    @PrimaryKey val movieId: Int
)