package com.pavellukyanov.cinematic.data.api.pojo.moviedetails.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class CastResponse(
    @SerialName("adult") var adult: Boolean,
    @SerialName("gender") var gender: Int,
    @SerialName("id") var id: Int,
    @SerialName("known_for_department") var knownForDepartment: String,
    @SerialName("name") var name: String,
    @SerialName("original_name") var originalName: String,
    @SerialName("popularity") var popularity: Double,
    @SerialName("profile_path") var profilePath: String? = null,
    @SerialName("cast_id") var castId: Int,
    @SerialName("character") var character: String,
    @SerialName("credit_id") var creditId: String,
    @SerialName("order") var order: Int
) {
    @Transient
    var profilePoster = ""
}