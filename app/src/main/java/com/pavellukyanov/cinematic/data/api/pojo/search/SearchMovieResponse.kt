package com.pavellukyanov.cinematic.data.api.pojo.search

import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieResponse(
    @SerialName("page") val page: Int? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("results") val results: List<MovieResponse?>? = null,
    @SerialName("total_results") val totalResults: Int? = null
)
