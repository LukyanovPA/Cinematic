package com.pavellukyanov.cinematic.data.database.dao.category

import androidx.room.*
import com.pavellukyanov.cinematic.data.database.entity.category.NowPlayingEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NowPlayingDao {
    @Query("SELECT * FROM now_playing WHERE movieId = :movieId")
    fun getMovie(movieId: Int): Single<NowPlayingEntity>

    @Query("SELECT * FROM now_playing")
    fun getAllMovies(): Single<List<NowPlayingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(popularMovieEntity: NowPlayingEntity): Completable

    @Update
    fun updateMovie(popularMovieEntity: NowPlayingEntity)

    @Query("DELETE FROM now_playing WHERE movieId = :movieId")
    fun deleteMovie(movieId: Int)
}