package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.search.SearchMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/movie")
    fun searchMovie(
        @Query("query") query: String
    ): Single<SearchMovieResponse>
}