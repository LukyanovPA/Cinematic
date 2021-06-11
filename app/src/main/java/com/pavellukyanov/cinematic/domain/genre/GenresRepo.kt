package com.pavellukyanov.cinematic.domain.genre

import io.reactivex.Single

interface GenresRepo {
    fun getGenres(): Single<List<Genre>>
}