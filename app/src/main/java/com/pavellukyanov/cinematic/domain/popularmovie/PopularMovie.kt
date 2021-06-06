package com.pavellukyanov.cinematic.domain.popularmovie

class PopularMovie(
    val id: Int,
    val originalTitle: String,
    val posterPath: String,
    var releaseDate : String,
    var voteAverage : Double
)