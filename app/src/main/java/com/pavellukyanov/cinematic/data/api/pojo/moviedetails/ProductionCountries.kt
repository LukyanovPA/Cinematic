package com.pavellukyanov.cinematic.data.api.pojo.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountries(
    @SerialName("iso_3166_1") var iso31661: String,
    @SerialName("name") var name: String
)