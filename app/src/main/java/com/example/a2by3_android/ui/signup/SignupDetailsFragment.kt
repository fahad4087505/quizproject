package com.example.a2by3_android.ui.signup

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentSignupDetailsBinding
import com.example.a2by3_android.repository.EmptyRepository

class SignupDetailsFragment : BaseFragment<FragmentSignupDetailsBinding , EmptyRepository>() {
  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSignupDetailsBinding {
    return FragmentSignupDetailsBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {

  }

  override fun onOptionsSelected(item: MenuItem) {

  }

  override fun onActivityCreation() {

  }

}