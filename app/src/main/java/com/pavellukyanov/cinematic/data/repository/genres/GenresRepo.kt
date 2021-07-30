package com.pavellukyanov.cinematic.data.repository.genres

import com.pavellukyanov.cinematic.domain.genre.Genre
import io.reactivex.Single

interface GenresRepo {
    fun getGenres(): Single<List<Genre>>
}