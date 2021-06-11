package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.genres.GenresResponse
import com.pavellukyanov.cinematic.utils.Language
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {
    @GET("genre/movie/list")
    fun getGenres(
        @Query("language") language: String = Language.LANGUAGE_RU
    ): Single<GenresResponse>
}