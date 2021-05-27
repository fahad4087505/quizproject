package com.example.a2by3_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2by3_android.base.BaseRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: BaseRepository) : ViewModel() {

  private val _text = MutableLiveData<String>().apply {
    value = "This is home Fragment"
  }
  val text: LiveData<String> = _text
}