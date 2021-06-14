package com.pavellukyanov.cinematic.data.api.pojo.genres

import com.google.gson.annotations.SerializedName

class GenresResponse(
    @SerializedName("genres") val genres: List<GenreResponse>
)