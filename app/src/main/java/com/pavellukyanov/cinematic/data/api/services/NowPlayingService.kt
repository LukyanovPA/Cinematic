package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.utils.Language
import com.pavellukyanov.myaaproject.data.models.NowPlayingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NowPlayingService {
    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("language") language: String = Language.LANGUAGE_RU,
        @Query("page") page: Int
    ): Single<NowPlayingResponse>
}