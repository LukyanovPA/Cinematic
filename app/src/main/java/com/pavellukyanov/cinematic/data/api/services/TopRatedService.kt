package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.toprated.TopRatedResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TopRatedService {
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("language") language: String = RequestParameters.LANGUAGE_RU,
        @Query("page") page: Int
    ): Single<TopRatedResponse>
}