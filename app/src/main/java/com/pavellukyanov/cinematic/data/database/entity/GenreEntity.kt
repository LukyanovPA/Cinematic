package com.pavellukyanov.cinematic.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pavellukyanov.cinematic.domain.genre.Genre

@Entity(tableName = "genres")
class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
)

fun GenreEntity.toGenre() = Genre(
    id = id,
    name = name
)