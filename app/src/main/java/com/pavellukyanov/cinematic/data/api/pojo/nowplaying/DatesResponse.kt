package com.pavellukyanov.cinematic.data.api.pojo.nowplaying

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatesResponse(
    @SerialName("maximum") var maximum: String,
    @SerialName("minimum") var minimum: String
)