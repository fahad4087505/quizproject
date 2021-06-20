package com.example.triviaquizapp.ui.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaquizapp.data.response.SellingProductListApiResponse
import com.example.triviaquizapp.model.Categories
import com.example.triviaquizapp.network.Resource
import com.example.triviaquizapp.repository.CategoriesListRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList

class CategoriesListViewModel @ViewModelInject constructor(private val repository: CategoriesListRepository) : ViewModel() {

  private val _sellingProductsList = MutableLiveData<Resource<SellingProductListApiResponse>>()
  val sellingProductsList = _sellingProductsList

  fun fetchSellingtProductsList() {
    viewModelScope.launch {
      repository.geSellingtProductsList().collect {
        _sellingProductsList.value = it
      }
    }
  }
  fun allCategories():ArrayList<Categories>
  {
    val categoriesArrayList: ArrayList<Categories> = ArrayList<Categories>()
    categoriesArrayList.add(Categories("10","Book"))
    categoriesArrayList.add(Categories("11","Film"))
    categoriesArrayList.add(Categories("12","Music"))
    categoriesArrayList.add(Categories("14","Television"))
    categoriesArrayList.add(Categories("20","Mythology"))
    categoriesArrayList.add(Categories("23","History"))
    categoriesArrayList.add(Categories("24","Politics"))
    categoriesArrayList.add(Categories("25","Art"))
    categoriesArrayList.add(Categories("19","Mathematics"))
    categoriesArrayList.add(Categories("27","Animals"))
    return categoriesArrayList
  }
}