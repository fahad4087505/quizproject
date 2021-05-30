package com.example.a2by3_android.ui.signup.onboarding.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a2by3_android.R
import com.example.a2by3_android.ui.signup.onboarding.CURRENT_SLIDE_IMAGE
import com.example.a2by3_android.ui.signup.onboarding.CURRENT_SLIDE_TEXT
import com.example.a2by3_android.ui.signup.onboarding.CurrentOnboardingSlideFragment

class OnboardingSliderAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

  val appInfo: Array<String> = arrayOf(fa.getString(R.string.list_your_cards_here), fa.getString(R.string.bid_buy_favorite_cards),
  fa.getString(R.string.track_your_card_investments), fa.getString(R.string.see_who_interested_in_cards))

  override fun getItemCount(): Int {
    return 4
  }

  override fun createFragment(position: Int): Fragment {
    val fragment = CurrentOnboardingSlideFragment()
    when (position) {
      0 -> {
        fragment.arguments = Bundle().apply {
          putString(CURRENT_SLIDE_TEXT , appInfo[0])
          putInt(CURRENT_SLIDE_IMAGE , R.drawable.ic_sell)
        }
      }
      1 -> {
        fragment.arguments = Bundle().apply {
          putString(CURRENT_SLIDE_TEXT , appInfo[1])
          putInt(CURRENT_SLIDE_IMAGE , R.drawable.ic_home)
        }
      }
      2 -> {
        fragment.arguments = Bundle().apply {
          putString(CURRENT_SLIDE_TEXT, appInfo[2])
          putInt(CURRENT_SLIDE_IMAGE, R.drawable.ic_dollar)
        }
      }
      3 -> {
        fragment.arguments = Bundle().apply {
          putString(CURRENT_SLIDE_TEXT, appInfo[3])
          putInt(CURRENT_SLIDE_IMAGE, R.drawable.ic_notification)
        }
      }
    }
    return fragment
  }
}