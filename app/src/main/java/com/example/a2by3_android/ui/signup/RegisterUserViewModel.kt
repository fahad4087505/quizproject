package com.example.a2by3_android.ui.signup

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2by3_android.data.registeruser.ApiUser
import com.example.a2by3_android.data.registeruser.RegisterUserApiResponse
import com.example.a2by3_android.network.Resource
import com.example.a2by3_android.repository.RegisterUserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterUserViewModel @ViewModelInject constructor(private val repository: RegisterUserRepository) : ViewModel() {

  private val userRegisterResponse = MutableLiveData<Resource<RegisterUserApiResponse>>()
  val registerUserResponse = userRegisterResponse

  fun registerUser(user: ApiUser) {
    viewModelScope.launch {
      repository.registerUser(user).collect {
        userRegisterResponse.value = it
      }
    }
  }
}