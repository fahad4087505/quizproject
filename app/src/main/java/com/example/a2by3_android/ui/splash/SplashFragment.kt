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
import java.util.concurrent.CountDownLatch

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
    goToOnboardingSliderFragment()
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

  private fun goToOnboardingSliderFragment() {
    object : CountDownTimer(4000 , 1000) {
      override fun onTick(millisUntilFinished: Long) {

      }

      override fun onFinish() {
        findNavController().navigate(R.id.action_splashFragment_to_onboardingSliderFragment)
      }

    }.start()
  }
}