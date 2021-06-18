package com.pavellukyanov.cinematic.data.api.pojo.toprated

import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRatedResponse(
    @SerialName("page") var page: Int,
    @SerialName("results") var results: List<MovieResponse>,
    @SerialName("total_pages") var totalPages: Int,
    @SerialName("total_results") var totalResults: Int
)