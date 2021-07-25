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
    this.genres.forEach { genreResponse ->
        mappingList.add(genreResponse.toGenre())
    }
    return mappingList
}

fun List<GenreEntity>.listEntityToListGenre(): List<Genre> {
    val mappingList = mutableListOf<Genre>()
    this.forEach { genreEntity ->
        mappingList.add(genreEntity.toGenre())
    }
    return mappingList
}

fun List<GenreResponse>.toListGenre(): List<Genre> {
    val mappingList = mutableListOf<Genre>()
    this.forEach { genreResponse ->
        mappingList.add(genreResponse.toGenre())
    }
    return mappingList
}