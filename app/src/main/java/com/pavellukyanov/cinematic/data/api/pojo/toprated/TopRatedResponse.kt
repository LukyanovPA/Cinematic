package com.pavellukyanov.cinematic.data.api.pojo.toprated

import com.google.gson.annotations.SerializedName
import com.pavellukyanov.cinematic.data.api.pojo.MovieResponse


data class TopRatedResponse (
   @SerializedName("page") var page : Int,
   @SerializedName("results") var results : List<MovieResponse>,
   @SerializedName("total_pages") var totalPages : Int,
   @SerializedName("total_results") var totalResults : Int
)