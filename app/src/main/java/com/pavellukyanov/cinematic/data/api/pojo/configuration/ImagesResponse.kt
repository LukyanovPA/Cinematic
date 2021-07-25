package com.pavellukyanov.cinematic.data.api.pojo.configuration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerialName("base_url") var baseUrl: String,
    @SerialName("secure_base_url") var secureBaseUrl: String,
    @SerialName("backdrop_sizes") var backdropSizes: List<String>,
    @SerialName("logo_sizes") var logoSizes: List<String>,
    @SerialName("poster_sizes") var posterSizes: List<String>,
    @SerialName("profile_sizes") var profileSizes: List<String>,
    @SerialName("still_sizes") var stillSizes: List<String>
)