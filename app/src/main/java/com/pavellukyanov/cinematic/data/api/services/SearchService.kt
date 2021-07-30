package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.search.SearchMovieResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/movie")
    fun searchMovie(
        @Query("language") language: String = RequestParameters.LANGUAGE_RU,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<SearchMovieResponse>
}