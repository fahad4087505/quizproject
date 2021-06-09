package com.example.a2by3_android.ui.signup.onboarding

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.a2by3_android.R
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

    btnNext.setOnClickListener {
      findNavController().navigate(R.id.action_onboardingSliderFragment_to_signupFragment)
    }

    val myPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        if (position == 3) {
          btnNext.visibility = View.VISIBLE
        } else {
          btnNext.visibility = View.GONE
        }
      }
    }

    viewPager.registerOnPageChangeCallback(myPageChangeCallback)

  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

}