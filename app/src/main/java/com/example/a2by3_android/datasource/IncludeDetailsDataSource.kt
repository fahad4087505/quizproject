package com.example.a2by3_android.datasource

import com.example.a2by3_android.apiservice.IncludeDetailsApiService
import com.example.a2by3_android.apiservice.SellingProductListApiService
import com.example.a2by3_android.base.BaseResponse
import javax.inject.Inject

class IncludeDetailsDataSource @Inject constructor
  (private val includeDetailsApiService: IncludeDetailsApiService): BaseResponse() {

    suspend fun getSellingtProductsList() = getResult {
      includeDetailsApiService.getSellingProductsList()
    }
}