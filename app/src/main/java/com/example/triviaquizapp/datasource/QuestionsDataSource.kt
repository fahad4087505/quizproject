package com.example.triviaquizapp.datasource
import com.example.triviaquizapp.apiservice.QuestionsApiService
import com.example.triviaquizapp.base.BaseResponse
import javax.inject.Inject

class QuestionsDataSource @Inject constructor
  (private val questionsApiService: QuestionsApiService): BaseResponse() {
    suspend fun getSellingtProductsList(hashMap: HashMap<String,String>) = getResult {
      questionsApiService.getQuestionsList(hashMap)
    }
}