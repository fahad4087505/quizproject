package com.example.a2by3_android.ui.signup.onboarding

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentOnboardingSliderBinding
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.ui.signup.onboarding.adapter.OnboardingSliderAdapter
import kotlinx.android.synthetic.main.fragment_onboarding_slider.*


class OnboardingSliderFragment : BaseFragment<FragmentOnboardingSliderBinding , EmptyRepository>() {
  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentOnboardingSliderBinding {
    return FragmentOnboardingSliderBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    val pagerAdapter = OnboardingSliderAdapter(requireActivity())
    viewPager.adapter = pagerAdapter

    indicator.setViewPager(viewPager)
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

}