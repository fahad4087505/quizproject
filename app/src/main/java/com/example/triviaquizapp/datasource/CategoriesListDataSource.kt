package com.example.triviaquizapp.datasource

import com.example.triviaquizapp.apiservice.CategoriesListApiService
import com.example.triviaquizapp.base.BaseResponse
import javax.inject.Inject

class CategoriesListDataSource @Inject constructor
  (private val sellingProductListApiService: CategoriesListApiService): BaseResponse() {

    suspend fun getSellingtProductsList() = getResult {
      sellingProductListApiService.getSellingProductsList()
    }
}