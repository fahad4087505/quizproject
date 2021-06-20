package com.example.triviaquizapp.ui.difficultylevel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaquizapp.data.response.SellingProductListApiResponse
import com.example.triviaquizapp.network.Resource
import com.example.triviaquizapp.repository.DifficultyLevelListRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DifficultyListViewModel @ViewModelInject constructor(private val repository: DifficultyLevelListRepository) : ViewModel() {

  private val _sellingProductsList = MutableLiveData<Resource<SellingProductListApiResponse>>()
  val sellingProductsList = _sellingProductsList

  fun fetchSellingtProductsList() {
    viewModelScope.launch {
      repository.geSellingtProductsList().collect {
        _sellingProductsList.value = it
      }
    }
  }
}