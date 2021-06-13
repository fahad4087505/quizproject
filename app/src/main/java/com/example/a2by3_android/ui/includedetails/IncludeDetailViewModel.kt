package com.example.a2by3_android.ui.includedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2by3_android.data.sellingproductlistapi.SellingProductListApiResponse
import com.example.a2by3_android.network.Resource
import com.example.a2by3_android.repository.IncludeDetailsRepository
import com.example.a2by3_android.repository.SellingProductListRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class IncludeDetailViewModel @ViewModelInject constructor(private val repository: IncludeDetailsRepository) : ViewModel() {

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