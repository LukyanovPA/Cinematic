package com.pavellukyanov.cinematic.data.api.services

import com.pavellukyanov.cinematic.data.api.pojo.configuration.ConfigurationResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ConfigurationService {
    @GET("configuration")
    fun getConfiguration(): Single<ConfigurationResponse>
}