package com.pavellukyanov.cinematic.data.database.dao

import androidx.room.*
import com.pavellukyanov.cinematic.data.database.entity.GenreEntity
import com.pavellukyanov.cinematic.data.database.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GenresDao {
    @Query("SELECT * FROM genres WHERE id = :id")
    fun getGenre(id: Int): Single<GenreEntity>

    @Query("SELECT * FROM genres")
    fun getAllGenres(): Single<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(photoEntity: GenreEntity): Completable

    @Update
    fun updateGenre(photoEntity: GenreEntity)

    @Query("DELETE FROM movie WHERE id = :id")
    fun deleteGenre(id: Int)
}