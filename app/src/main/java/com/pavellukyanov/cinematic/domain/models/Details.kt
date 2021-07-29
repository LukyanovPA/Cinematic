package com.pavellukyanov.cinematic.domain.models

import com.pavellukyanov.cinematic.domain.genre.Genre

class Details(
    val id : Int,
    val title : String,
    val originalTitle: String,
    val posterPath : String,
    val voteAverage : Double,
    val releaseDate : String,
    val overview : String,
    val genres: List<Genre>
)