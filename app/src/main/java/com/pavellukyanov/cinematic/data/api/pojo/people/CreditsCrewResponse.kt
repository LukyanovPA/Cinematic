package com.pavellukyanov.cinematic.data.api.pojo.people

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreditsCrewResponse(
    @SerialName("original_language") val original_language: String,
    @SerialName("original_title") val original_title: String,
    @SerialName("poster_path") val poster_path: String,
    @SerialName("video") val video: Boolean,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("release_date") val release_date: String,
    @SerialName("vote_count") val vote_count: Int,
    @SerialName("vote_average") val vote_average: Double,
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdrop_path: String,
    @SerialName("id") val id: Int,
    @SerialName("genre_ids") val genre_ids: List<Int>,
    @SerialName("popularity") val popularity: Double,
    @SerialName("credit_id") val credit_id: String,
    @SerialName("department") val department: String,
    @SerialName("job") val job: String
)