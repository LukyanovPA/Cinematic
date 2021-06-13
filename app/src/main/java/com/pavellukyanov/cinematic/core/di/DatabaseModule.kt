package com.pavellukyanov.cinematic.core.di

import android.content.Context
import androidx.room.Room
import com.pavellukyanov.cinematic.data.database.MovieDatabase
import com.pavellukyanov.cinematic.data.database.dao.GenresDao
import com.pavellukyanov.cinematic.data.database.dao.MovieDao
import com.pavellukyanov.cinematic.data.database.dao.category.NowPlayingDao
import com.pavellukyanov.cinematic.data.database.dao.category.PopularMovieDao
import com.pavellukyanov.cinematic.data.database.dao.category.TopRatedDao
import com.pavellukyanov.cinematic.data.database.dao.category.UpcomingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "MovieDatabase.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    // Provides Dao
    @Provides
    fun provideMovies(database: MovieDatabase): MovieDao =
        database.movies()

    @Provides
    fun provideGenres(database: MovieDatabase): GenresDao =
        database.genres()

    @Provides
    fun providePopularMovie(database: MovieDatabase): PopularMovieDao =
        database.popularMovie()

    @Provides
    fun provideNowPlaying(database: MovieDatabase): NowPlayingDao =
        database.nowPlaying()

    @Provides
    fun provideUpcoming(database: MovieDatabase): UpcomingDao =
        database.upcoming()

    @Provides
    fun provideTopRated(database: MovieDatabase): TopRatedDao =
        database.topRated()
}