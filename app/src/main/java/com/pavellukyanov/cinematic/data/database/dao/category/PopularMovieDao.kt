package com.pavellukyanov.cinematic.data.database.dao.category

import androidx.room.*
import com.pavellukyanov.cinematic.data.database.entity.category.PopularMovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PopularMovieDao {
    @Query("SELECT * FROM popular_movie WHERE movieId = :movieId")
    fun getMovie(movieId: Int): Single<PopularMovieEntity>

    @Query("SELECT * FROM popular_movie")
    fun getAllMovies(): Single<List<PopularMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(popularMovieEntity: PopularMovieEntity): Completable

    @Update
    fun updateMovie(popularMovieEntity: PopularMovieEntity)

    @Query("DELETE FROM popular_movie WHERE movieId = :movieId")
    fun deleteMovie(movieId: Int): Completable

    @Query("DELETE FROM popular_movie")
    fun deleteTable(): Completable
}