package com.pavellukyanov.cinematic.domain.models

data class Actor(
    val id: Int,
    var name: String,
    var profilePath: String,
    var character: String,
)