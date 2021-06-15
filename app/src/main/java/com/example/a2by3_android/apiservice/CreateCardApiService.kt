package com.example.a2by3_android.apiservice

import com.example.a2by3_android.data.sellingproductlistapi.SellingProductListApiResponse
import com.example.a2by3_android.data.sellingproductlistapi.createcardapi.CreateCardApiResponse
import com.example.a2by3_android.model.CreateCard
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CreateCardApiService {

  @POST("create-card")
  suspend fun createCard(@Body createCard: CreateCard): Response<CreateCardApiResponse>
}