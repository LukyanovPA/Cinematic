package com.pavellukyanov.cinematic.data.api.pojo.moviedetails

import com.google.gson.annotations.SerializedName

   
data class SpokenLanguages (

   @SerializedName("english_name") var englishName : String,
   @SerializedName("iso_639_1") var iso6391 : String,
   @SerializedName("name") var name : String

)