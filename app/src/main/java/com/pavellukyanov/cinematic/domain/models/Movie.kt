package com.pavellukyanov.cinematic.domain.models

data class Movie(
    val id: Int,
    var title: String,
    val posterPath: String,
    val releaseDate: String?,
    val voteAverage: Double
)