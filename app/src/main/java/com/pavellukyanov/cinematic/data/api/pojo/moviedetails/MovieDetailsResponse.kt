package com.pavellukyanov.cinematic.data.api.pojo.moviedetails

import com.pavellukyanov.cinematic.data.api.pojo.genres.GenreResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class MovieDetailsResponse(
    @SerialName("adult") var adult: Boolean,
    @SerialName("backdrop_path") var backdropPath: String? = null,
    @SerialName("belongs_to_collection") var belongsToCollection: BelongsToCollection? = null,
    @SerialName("budget") var budget: Int,
    @SerialName("genres") var genres: List<GenreResponse>,
    @SerialName("homepage") var homepage: String,
    @SerialName("id") var id: Int,
    @SerialName("imdb_id") var imdbId: String? = null,
    @SerialName("original_language") var originalLanguage: String,
    @SerialName("original_title") var originalTitle: String,
    @SerialName("overview") var overview: String,
    @SerialName("popularity") var popularity: Double,
    @SerialName("poster_path") var posterPath: String? = null,
    @SerialName("production_companies") var productionCompanies: List<ProductionCompanies>,
    @SerialName("production_countries") var productionCountries: List<ProductionCountries>,
    @SerialName("release_date") var releaseDate: String,
    @SerialName("revenue") var revenue: Int,
    @SerialName("runtime") var runtime: Int,
    @SerialName("spoken_languages") var spokenLanguages: List<SpokenLanguages>,
    @SerialName("status") var status: String,
    @SerialName("tagline") var tagline: String,
    @SerialName("title") var title: String,
    @SerialName("video") var video: Boolean,
    @SerialName("vote_average") var voteAverage: Double,
    @SerialName("vote_count") var voteCount: Int
) {
    @Transient
    var moviePoster = ""
}