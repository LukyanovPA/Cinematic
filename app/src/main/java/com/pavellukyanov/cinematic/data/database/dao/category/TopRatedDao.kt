package com.pavellukyanov.cinematic.data.database.dao.category

import androidx.room.*
import com.pavellukyanov.cinematic.data.database.entity.category.TopRatedEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TopRatedDao {
    @Query("SELECT * FROM top_rated WHERE movieId = :movieId")
    fun getMovie(movieId: Int): Single<TopRatedEntity>

    @Query("SELECT * FROM top_rated")
    fun getAllMovies(): Single<List<TopRatedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(popularMovieEntity: TopRatedEntity): Completable

    @Update
    fun updateMovie(popularMovieEntity: TopRatedEntity)

    @Query("DELETE FROM top_rated WHERE movieId = :movieId")
    fun deleteMovie(movieId: Int)

    @Query("DELETE FROM top_rated")
    fun deleteTable(): Completable
}