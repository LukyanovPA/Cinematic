package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.MovieDetailsResponse
import com.pavellukyanov.cinematic.data.api.pojo.moviedetails.credits.CreditsResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsService {
    @GET("movie/{movie_id}?")
    fun getMovieId(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = RequestParameters.LANGUAGE_RU
    ): Single<MovieDetailsResponse>

    @GET("movie/{movie_id}/credits")
    fun getCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = RequestParameters.LANGUAGE_RU
    ): Single<CreditsResponse>
}