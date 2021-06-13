package com.pavellukyanov.cinematic.data.repository.genres

import com.pavellukyanov.cinematic.data.api.pojo.genres.GenreResponse
import com.pavellukyanov.cinematic.data.api.pojo.genres.GenresResponse
import com.pavellukyanov.cinematic.data.database.entity.GenreEntity
import com.pavellukyanov.cinematic.domain.genre.Genre

fun GenreResponse.toGenre() = Genre(
    id = id,
    name = name
)

fun GenreEntity.toGenre() = Genre(
    id = id,
    name = name
)

fun Genre.toGenreEntity() = GenreEntity(
    id = id,
    name = name
)

fun GenresResponse.toListGenre(): List<Genre> {
    val mappingList = mutableListOf<Genre>()
    this.genres.forEach {
        mappingList.add(it.toGenre())
    }
    return mappingList
}

fun List<GenreEntity>.toListGenre(): List<Genre> {
    val mappingList = mutableListOf<Genre>()
    this.forEach {
        mappingList.add(it.toGenre())
    }
    return mappingList
}