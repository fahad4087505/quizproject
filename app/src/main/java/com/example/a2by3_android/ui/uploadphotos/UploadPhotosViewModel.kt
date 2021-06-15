package com.example.a2by3_android.ui.uploadphotos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2by3_android.data.sellingproductlistapi.SellingProductListApiResponse
import com.example.a2by3_android.data.sellingproductlistapi.createcardapi.CreateCardApiResponse
import com.example.a2by3_android.model.CreateCard
import com.example.a2by3_android.network.Resource
import com.example.a2by3_android.repository.CreateCardRepository
import com.example.a2by3_android.repository.IncludeDetailsRepository
import com.example.a2by3_android.repository.SellingProductListRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UploadPhotosViewModel @ViewModelInject constructor(private val repository: CreateCardRepository) : ViewModel() {

  private val _createCardApiResponse = MutableLiveData<Resource<CreateCardApiResponse>>()
  val createCardApiResponse = _createCardApiResponse

  fun createCardApiCall(createCard: CreateCard) {
    viewModelScope.launch {
      repository.createCardApiCall(createCard).collect {
        _createCardApiResponse.value = it
      }
    }
  }
}