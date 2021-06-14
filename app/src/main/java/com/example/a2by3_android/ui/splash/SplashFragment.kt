package com.example.a2by3_android.ui.splash

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.a2by3_android.R
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentSplashBinding
import com.example.a2by3_android.repository.EmptyRepository
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : BaseFragment<FragmentSplashBinding , EmptyRepository>() {
  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSplashBinding {
    return FragmentSplashBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    goToNextScreen()
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

  private fun goToNextScreen() {
    val user = FirebaseAuth.getInstance().currentUser

    object : CountDownTimer(4000 , 1000) {
      override fun onTick(millisUntilFinished: Long) {

      }

      override fun onFinish() {
        if (user != null) {
          // User is signed in
          findNavController().navigate(R.id.action_splashFragment_to_sellingProductListFragment)
        } else {
          // No user is signed in
          findNavController().navigate(R.id.action_splashFragment_to_onboardingSliderFragment)
        }

      }
    }.start()
  }
}