package com.pavellukyanov.cinematic.data.api

import com.pavellukyanov.cinematic.data.api.pojo.PopularMovieResponse
import com.pavellukyanov.cinematic.utils.Language
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("language") language: String = Language.LANGUAGE_RU,
        @Query("page") page: Int
    ): Single<PopularMovieResponse>
}