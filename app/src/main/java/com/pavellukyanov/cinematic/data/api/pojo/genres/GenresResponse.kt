package com.pavellukyanov.cinematic.data.api.pojo.genres

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresResponse(
    @SerialName("genres") val genres: List<GenreResponse>
)