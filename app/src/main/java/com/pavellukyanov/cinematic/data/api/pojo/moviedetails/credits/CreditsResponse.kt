package com.pavellukyanov.cinematic.data.api.pojo.moviedetails.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
    @SerialName("id") var id: Int,
    @SerialName("cast") var cast: List<CastResponse>,
    @SerialName("crew") var crew: List<CrewResponse>
)