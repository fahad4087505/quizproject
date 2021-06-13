package com.example.a2by3_android.repository

import com.example.a2by3_android.base.BaseRepository
import com.example.a2by3_android.data.sellingproductlistapi.SellingProductListApiResponse
import com.example.a2by3_android.datasource.IncludeDetailsDataSource
import com.example.a2by3_android.datasource.SellingProductListDataSource
import com.example.a2by3_android.network.Resource
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class IncludeDetailsRepository @Inject constructor(
  private val includeDetailsDataSource: IncludeDetailsDataSource
) : BaseRepository() {

  suspend fun geSellingtProductsList(): Flow<Resource<SellingProductListApiResponse>> {
    return flow {
      emit(Resource.loading())
      val result = includeDetailsDataSource.getSellingtProductsList()
      emit(result)
    }.flowOn(Dispatchers.IO)
  }
}