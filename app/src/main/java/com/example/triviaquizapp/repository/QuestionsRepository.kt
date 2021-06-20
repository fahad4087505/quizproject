package com.example.triviaquizapp.repository

import com.example.triviaquizapp.base.BaseRepository
import com.example.triviaquizapp.data.response.questionsmodel.QuestionApiResponse
import com.example.triviaquizapp.datasource.QuestionsDataSource
import com.example.triviaquizapp.network.Resource
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuestionsRepository @Inject constructor(private val questionsDataSource: QuestionsDataSource) : BaseRepository() {

  suspend fun getQuestionsList(hashMap: HashMap<String,String>): Flow<Resource<QuestionApiResponse>> {
    return flow {
      emit(Resource.loading())
      val result = questionsDataSource.getSellingtProductsList(hashMap)
      emit(result)
    }.flowOn(Dispatchers.IO)
  }
}