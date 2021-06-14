package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.upcoming.UpcomingResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingService {
    @GET("movie/upcoming")
    fun getUpcoming(
        @Query("language") language: String = RequestParameters.LANGUAGE_RU,
        @Query("page") page: Int,
        @Query("region") region: String = RequestParameters.REGION_RU
    ): Single<UpcomingResponse>
}