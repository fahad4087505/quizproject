package com.example.triviaquizapp.ui.questions


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaquizapp.data.response.questionsmodel.QuestionApiResponse
import com.example.triviaquizapp.network.Resource
import com.example.triviaquizapp.repository.QuestionsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuestionsViewModel @ViewModelInject constructor(private val repository: QuestionsRepository) : ViewModel() {

     val _questionsMutableList = MutableLiveData<Resource<QuestionApiResponse>>()

    fun fetchQuestionsList(hashMap: HashMap<String,String>) {
        viewModelScope.launch {
            repository.getQuestionsList(hashMap).collect {
                _questionsMutableList.value = it
            }
        }
    }
}