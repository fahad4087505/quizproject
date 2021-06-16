package com.example.a2by3_android.data.sellingproductlistapi

import com.google.gson.annotations.SerializedName

data class SellingProductListApiResponse (

  @SerializedName("status")
  val status : Boolean ,
  @SerializedName("data")
  val data : ArrayList<Category>
)

data class Category(
  val additional_fields: String,
  val created_at: String,
  val id: Int,
  val image: String,
  val sort: Int,
  val title: String,
  val updated_at: String
)