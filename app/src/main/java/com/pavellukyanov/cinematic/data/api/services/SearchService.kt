package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.search.SearchResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/multi")
    fun search(
        @Query("language") language: String = RequestParameters.LANGUAGE_RU,
        @Query("query") query: String
    ): Single<SearchResponse>
}