package com.example.triviaquizapp.repository

import com.example.triviaquizapp.base.BaseRepository
import com.example.triviaquizapp.data.response.SellingProductListApiResponse
import com.example.triviaquizapp.datasource.CategoriesListDataSource
import com.example.triviaquizapp.network.Resource
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DifficultyLevelListRepository @Inject constructor(private val sellingProductListDataSource: CategoriesListDataSource):BaseRepository() {

  suspend fun geSellingtProductsList(): Flow<Resource<SellingProductListApiResponse>> {
    return flow {
      emit(Resource.loading())
      val result = sellingProductListDataSource.getSellingtProductsList()
      emit(result)
    }.flowOn(Dispatchers.IO)
  }
}