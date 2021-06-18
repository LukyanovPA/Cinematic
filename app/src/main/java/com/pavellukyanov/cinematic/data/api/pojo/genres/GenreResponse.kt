package com.pavellukyanov.cinematic.data.api.pojo.genres

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenreResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)