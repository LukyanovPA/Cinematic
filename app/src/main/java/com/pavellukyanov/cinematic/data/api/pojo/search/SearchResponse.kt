package com.pavellukyanov.cinematic.data.api.pojo.search

import com.pavellukyanov.cinematic.data.api.pojo.configuration.ConfigurationResponse
import com.pavellukyanov.cinematic.domain.search.SearchItem
import com.pavellukyanov.cinematic.utils.PosterSizeList
import com.pavellukyanov.cinematic.utils.PosterSizes
import com.pavellukyanov.cinematic.utils.SearchItemType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("page") val page: Int? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("results") val results: List<ResultsItem?>? = null,
    @SerialName("total_results") val totalResults: Int? = null
)

@Serializable
data class ResultsItem(
    @SerialName("overview") val overview: String? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int?>? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("media_type") val mediaType: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("vote_average") val voteAverage: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("origin_country") val originCountry: List<String?>? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("gender") val gender: Int? = null,
    @SerialName("known_for_department") val knownForDepartment: String? = null,
    @SerialName("known_for") val knownFor: List<KnownForItem?>? = null,
    @SerialName("profile_path") val profilePath: String? = null
)

@Serializable
data class KnownForItem(
    @SerialName("overview") val overview: String? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int?>? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("media_type") val mediaType: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("vote_count") val voteCount: Int? = null
)

fun ResultsItem.toSearchItem(config: ConfigurationResponse): SearchItem {
    return SearchItem(
        when (mediaType) {
            "movie" -> title!!
            else -> name!!
        },
        when (mediaType) {
            "movie", "TV" -> setupImage(config, posterPath!!)
            else -> setupImage(config, profilePath!!)
        },
        when (mediaType) {
            "movie" -> SearchItemType.MOVIE
            "tv" -> SearchItemType.TV
            else -> SearchItemType.PERSON
        }
    )
}

fun ResultsItem.setupImage(
    config: ConfigurationResponse,
    path: String
): String {
    PosterSizeList.posterSizes = config.images.posterSizes
    return "${config.images.baseUrl}/${PosterSizes.ORIGINAL.size}/$path"
}
