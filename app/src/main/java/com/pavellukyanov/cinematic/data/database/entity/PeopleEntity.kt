package com.pavellukyanov.cinematic.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peoples")
class PeopleEntity(
    @PrimaryKey val id: Int,
    val biography: String,
    val birthday: String,
    val deathday: String?,
    val age: String,
    val name: String,
    val place_of_birth: String,
    val profile_path: String
)