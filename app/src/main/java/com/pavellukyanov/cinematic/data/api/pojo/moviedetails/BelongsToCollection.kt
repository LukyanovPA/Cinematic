package com.pavellukyanov.cinematic.data.api.pojo.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BelongsToCollection(
    @SerialName("id") var id: Int,
    @SerialName("name") var name: String,
    @SerialName("poster_path") var posterPath: String? = null,
    @SerialName("backdrop_path") var backdropPath: String? = null
)