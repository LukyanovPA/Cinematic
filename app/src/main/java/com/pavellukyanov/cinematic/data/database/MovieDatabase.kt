package com.pavellukyanov.cinematic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavellukyanov.cinematic.data.database.dao.GenresDao
import com.pavellukyanov.cinematic.data.database.dao.MovieDao
import com.pavellukyanov.cinematic.data.database.dao.category.NowPlayingDao
import com.pavellukyanov.cinematic.data.database.dao.category.PopularMovieDao
import com.pavellukyanov.cinematic.data.database.dao.category.TopRatedDao
import com.pavellukyanov.cinematic.data.database.dao.category.UpcomingDao
import com.pavellukyanov.cinematic.data.database.entity.GenreEntity
import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.NowPlayingEntity
import com.pavellukyanov.cinematic.data.database.entity.category.PopularMovieEntity
import com.pavellukyanov.cinematic.data.database.entity.category.TopRatedEntity
import com.pavellukyanov.cinematic.data.database.entity.category.UpcomingEntity

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        PopularMovieEntity::class,
        NowPlayingEntity::class,
        UpcomingEntity::class,
        TopRatedEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movies(): MovieDao
    abstract fun genres(): GenresDao
    abstract fun popularMovie(): PopularMovieDao
    abstract fun nowPlaying(): NowPlayingDao
    abstract fun upcoming(): UpcomingDao
    abstract fun topRated(): TopRatedDao
}