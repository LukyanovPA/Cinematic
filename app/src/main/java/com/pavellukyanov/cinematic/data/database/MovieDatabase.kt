package com.pavellukyanov.cinematic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavellukyanov.cinematic.data.database.dao.PopularMovieDao
import com.pavellukyanov.cinematic.data.database.entity.PopularMovieEntity

@Database(
    entities = [
        PopularMovieEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun popularMovie(): PopularMovieDao
}