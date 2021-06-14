package com.pavellukyanov.cinematic.data.database.dao.category

import androidx.room.*
import com.pavellukyanov.cinematic.data.database.entity.category.UpcomingEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UpcomingDao {
    @Query("SELECT * FROM upcoming WHERE movieId = :movieId")
    fun getMovie(movieId: Int): Single<UpcomingEntity>

    @Query("SELECT * FROM upcoming")
    fun getAllMovies(): Single<List<UpcomingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(popularMovieEntity: UpcomingEntity): Completable

    @Update
    fun updateMovie(popularMovieEntity: UpcomingEntity)

    @Query("DELETE FROM upcoming WHERE movieId = :movieId")
    fun deleteMovie(movieId: Int)

    @Query("DELETE FROM upcoming")
    fun deleteTable(): Completable
}