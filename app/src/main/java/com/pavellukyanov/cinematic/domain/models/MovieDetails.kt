package com.pavellukyanov.cinematic.domain.models

class MovieDetails(
    val details: Details,
    val actors: List<Actor>,
    val crew: List<Crew>
)