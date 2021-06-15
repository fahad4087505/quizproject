package com.example.a2by3_android.repository

import com.example.a2by3_android.base.BaseRepository
import com.example.a2by3_android.data.sellingproductlistapi.SellingProductListApiResponse
import com.example.a2by3_android.data.sellingproductlistapi.createcardapi.CreateCardApiResponse
import com.example.a2by3_android.datasource.CreateCardDataSource
import com.example.a2by3_android.datasource.SellingProductListDataSource
import com.example.a2by3_android.model.CreateCard
import com.example.a2by3_android.network.Resource
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreateCardRepository @Inject constructor(
  private val createCardDataSource: CreateCardDataSource
) : BaseRepository() {

  suspend fun createCardApiCall(createCard: CreateCard): Flow<Resource<CreateCardApiResponse>> {
    return flow {
      emit(Resource.loading())
      val result = createCardDataSource.createCard(createCard)
      emit(result)
    }.flowOn(Dispatchers.IO)
  }
}