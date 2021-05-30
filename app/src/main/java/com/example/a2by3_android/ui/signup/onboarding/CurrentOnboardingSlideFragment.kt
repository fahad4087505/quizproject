package com.example.a2by3_android.ui.signup.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2by3_android.R
import kotlinx.android.synthetic.main.fragment_current_onboarding_slide.*

const val  CURRENT_SLIDE_IMAGE = "current_slide_image"
const val CURRENT_SLIDE_TEXT = "current_slide_text"

class CurrentOnboardingSlideFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_current_onboarding_slide, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val text = arguments?.getString(CURRENT_SLIDE_TEXT)
    val image = arguments?.getInt(CURRENT_SLIDE_IMAGE)

    text?.let {
      tvAppDetail.text = it
    }
    image?.let {
      ivAppDetailIcons.setImageResource(it)
    }
  }
}