package com.pavellukyanov.cinematic.data.api.pojo.upcoming

import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse
import com.pavellukyanov.cinematic.data.api.pojo.nowplaying.DatesResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingResponse(
    @SerialName("dates") var dates: DatesResponse,
    @SerialName("page") var page: Int,
    @SerialName("results") var results: List<MovieResponse>,
    @SerialName("total_pages") var totalPages: Int,
    @SerialName("total_results") var totalResults: Int
)