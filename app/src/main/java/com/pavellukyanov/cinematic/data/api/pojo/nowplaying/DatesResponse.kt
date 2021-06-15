package com.pavellukyanov.cinematic.data.api.pojo.nowplaying

import com.google.gson.annotations.SerializedName

   
data class DatesResponse (
   @SerializedName("maximum") var maximum : String,
   @SerializedName("minimum") var minimum : String
)