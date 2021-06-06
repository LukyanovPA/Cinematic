package com.pavellukyanov.cinematic.data.database.dao

import androidx.room.*
import com.pavellukyanov.cinematic.data.database.entity.PopularMovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PopularMovieDao {
    @Query("SELECT * FROM popular_movie WHERE id = :id")
    fun getMovie(id: Int): Flowable<PopularMovieEntity>

    @Query("SELECT * FROM popular_movie")
    fun getAllMovies(): Flowable<List<PopularMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(photoEntity: PopularMovieEntity): Completable

    @Update
    fun updateMovie(photoEntity: PopularMovieEntity)

    @Query("DELETE FROM popular_movie WHERE id = :id")
    fun deleteMovie(id: Int)
}