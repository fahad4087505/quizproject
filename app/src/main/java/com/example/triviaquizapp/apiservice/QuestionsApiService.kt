package com.example.triviaquizapp.apiservice

import com.example.triviaquizapp.data.response.questionsmodel.QuestionApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.HashMap

interface QuestionsApiService {
  @GET("api.php")
  suspend fun getQuestionsList(@QueryMap hashMap: HashMap<String,String> ): Response<QuestionApiResponse>
}