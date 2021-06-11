package com.example.a2by3_android.apiservice

import com.example.a2by3_android.data.sellingproductlistapi.SellingProductListApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface SellingProductListApiService {

  @GET("category-list")
  suspend fun getSellingProductsList(): Response<SellingProductListApiResponse>
}