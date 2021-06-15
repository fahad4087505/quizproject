package com.example.a2by3_android.datasource
import com.example.a2by3_android.apiservice.CreateCardApiService
import com.example.a2by3_android.apiservice.IncludeDetailsApiService
import com.example.a2by3_android.apiservice.SellingProductListApiService
import com.example.a2by3_android.base.BaseResponse
import com.example.a2by3_android.model.CreateCard
import javax.inject.Inject

class CreateCardDataSource @Inject constructor
  (private val createCardApiService: CreateCardApiService): BaseResponse() {
    suspend fun createCard(createCard: CreateCard) = getResult {
      createCardApiService.createCard(createCard)
    }
}