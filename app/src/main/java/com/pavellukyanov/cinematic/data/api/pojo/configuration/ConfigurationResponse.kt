package com.pavellukyanov.cinematic.data.api.pojo.configuration

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
    @SerializedName("images") var images : ImagesResponse,
    @SerializedName("change_keys") var changeKeys : List<String>
)