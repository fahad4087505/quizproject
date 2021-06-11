package com.example.a2by3_android.datasource

import com.example.a2by3_android.apiservice.SellingProductListApiService
import com.example.a2by3_android.base.BaseResponse
import javax.inject.Inject

class SellingProductListDataSource @Inject constructor
  (private val sellingProductListApiService: SellingProductListApiService): BaseResponse() {

    suspend fun getSellingtProductsList() = getResult {
      sellingProductListApiService.getSellingProductsList()
    }
}