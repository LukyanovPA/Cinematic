package com.pavellukyanov.cinematic.data.api.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class MovieResponse(
    @SerialName("adult") var adult: Boolean,
    @SerialName("backdrop_path") var backdropPath: String? = null,
    @SerialName("genre_ids") var genreIds: List<Int>,
    @SerialName("id") var id: Int,
    @SerialName("original_language") var originalLanguage: String,
    @SerialName("original_title") var originalTitle: String,
    @SerialName("overview") var overview: String,
    @SerialName("popularity") var popularity: Double,
    @SerialName("poster_path") var posterPath: String? = null,
    @SerialName("release_date") var releaseDate: String? = null,
    @SerialName("title") var title: String,
    @SerialName("video") var video: Boolean,
    @SerialName("vote_average") var voteAverage: Double,
    @SerialName("vote_count") var voteCount: Int
) {
    @Transient
    var moviePoster = ""
}
