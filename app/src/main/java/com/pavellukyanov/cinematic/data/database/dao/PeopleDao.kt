package com.pavellukyanov.cinematic.data.database.dao

import androidx.room.*
import com.pavellukyanov.cinematic.data.database.entity.PeopleEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PeopleDao {
    @Query("SELECT * FROM peoples WHERE id = :id")
    fun getPeople(id: Int): Single<PeopleEntity>

    @Query("SELECT * FROM peoples")
    fun getAllPeoples(): Single<List<PeopleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(people: PeopleEntity): Completable

    @Update
    fun updatePeople(movie: PeopleEntity)

    @Query("DELETE FROM peoples WHERE id = :id")
    fun deletePeople(id: Int)
}