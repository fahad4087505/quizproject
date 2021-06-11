package com.example.a2by3_android.data.sellingproductlistapi

import com.google.gson.annotations.SerializedName

data class SellingProductListApiResponse (

  @SerializedName("status")
  val status : Boolean ,
  @SerializedName("data")
  val data : ArrayList<String>
)