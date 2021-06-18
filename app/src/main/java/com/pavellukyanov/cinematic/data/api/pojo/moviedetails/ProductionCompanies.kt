package com.pavellukyanov.cinematic.data.api.pojo.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanies(
    @SerialName("id") var id: Int,
    @SerialName("logo_path") var logoPath: String? = null,
    @SerialName("name") var name: String,
    @SerialName("origin_country") var originCountry: String
)