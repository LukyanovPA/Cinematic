package com.pavellukyanov.cinematic.domain.models

class PeopleDetails(
    val id: Int,
    val biography: String,
    val birthday: String,
    val deathday: String?,
    val age: String,
    val name: String,
    val place_of_birth: String,
    val profile_path: String
)