package com.pavellukyanov.cinematic.data.api.pojo.people

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PeopleResponse(
    @SerialName("adult") val adult: Boolean,
    @SerialName("also_known_as") val also_known_as: List<String>,
    @SerialName("biography") val biography: String,
    @SerialName("birthday") val birthday: String,
    @SerialName("deathday") val deathday: String,
    @SerialName("gender") val gender: Int,
    @SerialName("homepage") val homepage: String,
    @SerialName("id") val id: Int,
    @SerialName("imdb_id") val imdb_id: String,
    @SerialName("known_for_department") val known_for_department: String,
    @SerialName("name") val name: String,
    @SerialName("place_of_birth") val place_of_birth: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("profile_path") val profile_path: String
)