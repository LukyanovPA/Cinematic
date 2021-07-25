package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.nowplaying.NowPlayingResponse
import com.pavellukyanov.cinematic.utils.RequestParameters
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NowPlayingService {
    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("language") language: String = RequestParameters.LANGUAGE_RU,
        @Query("page") page: Int
    ): Single<NowPlayingResponse>
}