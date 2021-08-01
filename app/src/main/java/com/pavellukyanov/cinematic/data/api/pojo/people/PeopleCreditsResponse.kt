package com.pavellukyanov.cinematic.data.api.pojo.people

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleCreditsResponse(
    @SerialName("cast") val cast: List<CreditsCastResponse>,
    @SerialName("crew") val crew: List<CreditsCrewResponse>,
    @SerialName("id") val id: Int
)