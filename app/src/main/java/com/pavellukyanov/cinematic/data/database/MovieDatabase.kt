package com.pavellukyanov.cinematic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavellukyanov.cinematic.data.database.dao.GenresDao
import com.pavellukyanov.cinematic.data.database.dao.MovieDao
import com.pavellukyanov.cinematic.data.database.dao.PeopleDao
import com.pavellukyanov.cinematic.data.database.entity.GenreEntity
import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.data.database.entity.PeopleEntity

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        PeopleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movies(): MovieDao
    abstract fun genres(): GenresDao
    abstract fun people(): PeopleDao
}