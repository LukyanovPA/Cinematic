package com.pavellukyanov.cinematic.data.database.dao

import androidx.room.*
import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovie(id: Int): Single<MovieEntity>

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Single<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>): Completable

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("DELETE FROM movie WHERE id = :id")
    fun deleteMovie(id: Int)
}