package com.pavellukyanov.cinematic.data.api.pojo.moviedetails.credits

import com.google.gson.annotations.SerializedName

   
data class CreditsResponse (
   @SerializedName("id") var id : Int,
   @SerializedName("cast") var cast : List<CastResponse>,
   @SerializedName("crew") var crew : List<CrewResponse>
)