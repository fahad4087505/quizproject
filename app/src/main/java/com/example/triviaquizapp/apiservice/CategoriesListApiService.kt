package com.example.triviaquizapp.apiservice

import com.example.triviaquizapp.data.response.SellingProductListApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesListApiService {
  @GET("category-list")
  suspend fun getSellingProductsList(): Response<SellingProductListApiResponse>
}