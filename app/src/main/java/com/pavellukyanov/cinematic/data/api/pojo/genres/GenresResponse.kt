package com.pavellukyanov.cinematic.data.api.pojo.genres

import com.google.gson.annotations.SerializedName
import com.pavellukyanov.cinematic.domain.genre.Genre

class GenresResponse(
    @SerializedName("genres") val genres: List<GenreResponse>
)

class GenreResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

fun GenreResponse.toGenre() = Genre(
    id = id,
    name = name
)