package com.example.a2by3_android.apiservice

import com.example.a2by3_android.data.registeruser.ApiUser
import com.example.a2by3_android.data.registeruser.RegisterUserApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterUserApiService {

  @POST("register-user")
  suspend fun registerUser(@Body user: ApiUser): Response<RegisterUserApiResponse>
}