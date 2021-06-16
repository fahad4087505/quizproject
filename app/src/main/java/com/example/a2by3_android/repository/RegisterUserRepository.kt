package com.example.a2by3_android.repository

import com.example.a2by3_android.base.BaseRepository
import com.example.a2by3_android.data.registeruser.ApiUser
import com.example.a2by3_android.data.registeruser.RegisterUserApiResponse
import com.example.a2by3_android.datasource.RegisterUserDataSource
import com.example.a2by3_android.network.Resource
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterUserRepository @Inject constructor(
  private val registerUserDataSource: RegisterUserDataSource
) : BaseRepository() {

  suspend fun registerUser(user: ApiUser): Flow<Resource<RegisterUserApiResponse>> {
    return flow {
      emit(Resource.loading())
      val result = registerUserDataSource.registerUser(user)
      emit(result)
    }.flowOn(Dispatchers.IO)
  }
}