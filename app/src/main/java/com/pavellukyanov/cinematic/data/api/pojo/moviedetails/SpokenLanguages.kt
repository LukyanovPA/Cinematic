package com.pavellukyanov.cinematic.data.api.pojo.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguages(
    @SerialName("english_name") var englishName: String,
    @SerialName("iso_639_1") var iso6391: String,
    @SerialName("name") var name: String
)