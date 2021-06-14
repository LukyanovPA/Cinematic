package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.genres.GenresResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {
    @GET("genre/movie/list")
    fun getGenres(
        @Query("language") language: String = RequestParameters.LANGUAGE_RU
    ): Single<GenresResponse>
}