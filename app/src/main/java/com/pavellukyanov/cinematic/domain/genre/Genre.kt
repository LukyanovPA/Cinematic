package com.pavellukyanov.cinematic.domain.genre

import com.pavellukyanov.cinematic.data.database.entity.GenreEntity

data class Genre(
    val id: Int,
    val name: String
)

fun Genre.toGenreEntity() = GenreEntity(
    id = id,
    name = name
)