package com.example.a2by3_android.ui.signup

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.a2by3_android.R
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentSignUpSuccessBinding
import com.example.a2by3_android.repository.EmptyRepository

class SignUpSuccessFragment : BaseFragment<FragmentSignUpSuccessBinding , EmptyRepository>() {
  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSignUpSuccessBinding {
    return FragmentSignUpSuccessBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    goToSignInFragment()
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

  private fun goToSignInFragment() {
    object : CountDownTimer(3000 , 1000) {
      override fun onTick(millisUntilFinished: Long) {
      }

      override fun onFinish() {
        findNavController().navigate(R.id.action_signUpSuccessFragment_to_signInFragment)
      }

    }.start()
  }
}