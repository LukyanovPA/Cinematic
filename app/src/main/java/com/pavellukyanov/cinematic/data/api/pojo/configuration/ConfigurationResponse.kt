package com.pavellukyanov.cinematic.data.api.pojo.configuration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images") var images : ImagesResponse,
    @SerialName("change_keys") var changeKeys : List<String>
)