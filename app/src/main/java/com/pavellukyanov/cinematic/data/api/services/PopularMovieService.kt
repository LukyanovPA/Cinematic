package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.popularmovie.PopularMovieResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovieService {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("language") language: String = RequestParameters.LANGUAGE_RU,
        @Query("page") page: Int
    ): Single<PopularMovieResponse>
}