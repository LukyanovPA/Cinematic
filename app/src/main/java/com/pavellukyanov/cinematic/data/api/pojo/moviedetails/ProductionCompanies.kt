package com.pavellukyanov.cinematic.data.api.pojo.moviedetails

import com.google.gson.annotations.SerializedName

   
data class ProductionCompanies (

   @SerializedName("id") var id : Int,
   @SerializedName("logo_path") var logoPath : String,
   @SerializedName("name") var name : String,
   @SerializedName("origin_country") var originCountry : String

)