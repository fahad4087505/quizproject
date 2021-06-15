package com.example.a2by3_android.data.sellingproductlistapi.createcardapi

import com.google.gson.annotations.SerializedName

data class CreateCardApiResponse (

  @SerializedName("status")
  val status : Boolean ,
  @SerializedName("data")
  val data : ArrayList<String>
)