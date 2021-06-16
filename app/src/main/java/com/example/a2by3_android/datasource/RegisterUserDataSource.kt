package com.example.a2by3_android.datasource

import com.example.a2by3_android.apiservice.RegisterUserApiService
import com.example.a2by3_android.base.BaseResponse
import com.example.a2by3_android.data.registeruser.ApiUser
import javax.inject.Inject

class RegisterUserDataSource @Inject constructor
  (private val registerUserApiService: RegisterUserApiService): BaseResponse() {

  suspend fun registerUser(user: ApiUser) = getResult {
    registerUserApiService.registerUser(user)
  }
}